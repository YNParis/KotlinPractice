package com.demos.yxn.lifecircle.Activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.telephony.TelephonyManager
import android.util.Log
import com.app808.HelloNdk
import com.demos.yxn.lifecircle.R
import kotlinx.android.synthetic.main.activity_ndkgpsdemo.*
import org.jetbrains.anko.toast
import java.io.IOException
import java.net.Socket
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 利用c语言文件，将定位数据转成固定格式传送
 * 步骤：
 * 1.网络连接：socket连接网络，ip、端口固定；
 * 2.初始化请求头：连接网络调用cinit()方法，初始化请求头及请求尾；
 * 3.登录：调用clogin()方法，传入userid等信息，获取格式化好的16进制bytearray，通过socket传输该数组；
 * 4.解析返回数据：调用cparse(strReceived: ByteArray)通用解析方法，解析服务器返回的数据，解析结果三种情况;
 * 5.发送GPS信息：调用cgetposition()方法，将得到的位置信息转换成固定格式的16进制bytearray，通过socket传输该数组；
 * 6.继续解析返回结果，与步骤4相同。
 */
class NDKGPSDemoActivity : AppCompatActivity(), SensorEventListener {


    private lateinit var locationManager: LocationManager//定位
    private lateinit var sensorManager: SensorManager//方向传感器
    private lateinit var orientationSensor: Sensor
    private lateinit var telephonyManager: TelephonyManager


    private val LOCATION_PERMISSION = 1
    val format = SimpleDateFormat("yyyyMMddHHmmss")

    var lon = 113.44
    var lat = 55.36
    var altitude = 20.1
    var direction = 0.0
    var speed = 0.3
    var time = "20181205094530"

    var lastlon = 113.44
    var lastlat = 55.36
    var lastaltitude = 20.1
    var lastdirection = 180.3
    var lastspeed = 0.3
    var lasttime = "20181205094530"

    //外网端口和外网ip

    private val UNICOM_IP = "114.255.212.48"
    private val TELECOM_IP = "106.39.36.167"
    private val MOBILE_IP = "114.255.212.72"
    private val PORT = 8348
    var ip = ""
    var imei = ""
    var date = Date()

    private val CON_SUCCESS = 1
    private val CON_FAILED = 2
    private val LOGIN_SUCCESS = 3
    private val LOGIN_FAILED = 4
    private val LOGIN_STRAIGHT = 5
    private val SEND_GPS = 6
    private val SEND_GPS_SUCCESS = 7
    private val SEND_LOGIN = 8


    private lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ndkgpsdemo)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        imei = telephonyManager.deviceId
        checkOperator(telephonyManager.subscriberId)

        //如果手机的SDK版本使用新的权限模型,检查是否获得了位置权限,如果没有就申请位置权限,如果有权限就刷新位置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val hasLocationPermission = checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
                //requestPermissions是异步执行的
                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                        LOCATION_PERMISSION)
            } else {
                updateLocation(locationManager)
            }
        } else {
            updateLocation(locationManager)
        }
        connect()
    }

    /**
     * 获取根据运营商选择ip
     */
    fun checkOperator(subscriberId: String) {
        if (subscriberId?.startsWith("46001")) {
            //联通网络
            ip = UNICOM_IP
        } else if (subscriberId?.startsWith("46003")) {
            //电信网络
            ip = TELECOM_IP
        } else {
            ip = MOBILE_IP
        }
    }

    /**
     * 在activity中显示定位信息
     */
    private fun showInfo() {
        tv_getposition.text = "lastinfo:lon=${lastlon},lat=${lastlat},altitude=${lastaltitude},diretion=${lastdirection},time=${lasttime},speed=${lastspeed}" +
                "\nthisinfo:lon=${lon},lat=${lat},altitude=${altitude},diretion=${direction},time=${time},speed=${speed}"
    }

    /**
     * 初始化请求格式，无返回
     */
    fun cinit() {
        HelloNdk.cInit("yangna", imei, 60, PORT, ip)
    }

    /**
     * 发送登录数据
     */
    fun clogin(): ByteArray {
        val ch = HelloNdk.cLogin()
        val len = HelloNdk.cLoginLen()
        return getRealArray(len, ch)
    }


    /**
     *  获取真实长度的数据，放入新的array中
     */
    fun getRealArray(size: Int, byteArray: ByteArray): ByteArray {
        var realchr = ByteArray(size)
        for (i in 0 until size) {
            realchr[i] = byteArray.get(i)
        }
        return realchr
    }

    /**
     * bytearray转换成16进制string打印
     */
    fun getByteHexString(byteArray: ByteArray): String {
        var tmp = ""
        var sb = StringBuilder("")
        byteArray.forEach { it ->
            tmp = Integer.toHexString(0xFF and it.toInt())
            if (tmp.length == 1) {
                tmp = "0" + tmp
            }
            sb.append(tmp)
        }
        Log.e("NDKDemo", sb.toString())
        return sb.toString()
    }

    /**
     * 解析从服务器拿到的数据
     * return int型,<0表示连接失败，重新连接网络，==0表示数据发送成功，>0时特殊情况，需要将刚才接收到的服务端的数据原封不动地返回去
     */
    fun cparse(strReceived: ByteArray): Int {
        return HelloNdk.cParseCmd(strReceived.size, strReceived)
    }

    /**
     * 手机端获取位置信息，解析成固定16进制字节数组的形式，进行网络传输
     */
    fun cgetposition(): ByteArray {
        val arr = HelloNdk.cGetPositon(lon, lat, altitude, speed, direction, time)
        val len = HelloNdk.cGetPositonLen(lon, lat, altitude, speed, direction, time)
        return getRealArray(len, arr)
    }

    //定义定位监听
    val locationListener = object : LocationListener {

        override fun onProviderDisabled(provider: String?) {
            toast("关闭了GPS")
        }

        override fun onProviderEnabled(provider: String?) {
            toast("打开了GPS")
            updateLocation(locationManager)
        }

        override fun onLocationChanged(location: Location?) {
            updateLocation(locationManager)
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }
    }

    override fun onPause() {
        super.onPause()
        val hasLocationPermission = checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        if ((locationManager != null) && ((hasLocationPermission == PackageManager.PERMISSION_GRANTED))) {
            locationManager.removeUpdates(locationListener)
        }
        if (sensorManager != null) {
            //解除注册
            sensorManager.unregisterListener(this, orientationSensor);
        }
    }

    override fun onResume() {
        //挂上LocationListener, 在状态变化时刷新位置显示,因为requestPermissionss是异步执行的,所以要先确认是否有权限
        super.onResume()
        val hasLocationPermission = checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        if ((locationManager != null) && ((hasLocationPermission == PackageManager.PERMISSION_GRANTED))) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10 * 1000, 0F, locationListener)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10 * 1000, 0F, locationListener)
            updateLocation(locationManager)
        }
        if (sensorManager != null) {
            //注册监听
            sensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    /**
     * 申请下位置权限后,要刷新位置信息
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toast("获取了位置权限")
                updateLocation(locationManager)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }


    override fun onSensorChanged(event: SensorEvent?) {
        //获取方向
        lastdirection = direction
        direction = event!!.values[0].toDouble()
        showInfo()
    }

    fun updateLocation(locationManager: LocationManager) {
        //更新信息，同时显示在tv上
        val location = getLocation(locationManager)
        if (location != null) {

            lastlon = lon
            lastlat = lat
            lastaltitude = altitude
            lastspeed = speed
            lasttime = time

            lon = location.longitude
            lat = location.latitude
            altitude = location.altitude
            speed = location.speed.toDouble()
            date.time = location.time
            time = format.format(date)

            showInfo()
        }
    }//获取位置信息

    fun getLocation(locationManager: LocationManager): Location? {
        var location: Location? = null
        if (this.checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            toast("没有位置权限")
        } else if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            toast("没有打开GPS")
        } else {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location == null) {
//                toast("位置信息为空")
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (location == null) {
                    toast("网络位置信息也为空")
                } else {
//                    toast("当前使用网络位置")
                }
            }
        }
        return location
    }


    /**
     * 进行网络连接
     */
    fun connect() {
        Thread {
            try {
                socket = Socket(ip, PORT)
                socket.setSoTimeout(10 * 1000)
                if (socket.isConnected) {
                    handler.sendEmptyMessage(CON_SUCCESS)
                }
            } catch (e: UnknownHostException) {
                handler.sendEmptyMessage(CON_FAILED)
                e.printStackTrace()
            } catch (e: IOException) {
                handler.sendEmptyMessage(CON_FAILED)
                e.printStackTrace()
            }
        }.start()

    }

    private val handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
                CON_SUCCESS -> {
                    toast("网络连接成功")
                    cinit()
                    sendLoginInfo(clogin())
                }
                CON_FAILED -> toast("网络连接失败")
                LOGIN_SUCCESS -> {
                    sendPositionInfo(cgetposition())
                }
                LOGIN_FAILED -> toast("登录时网络连接失败")
                LOGIN_STRAIGHT -> toast("需要将得到的数据直接返回服务端")
                SEND_GPS_SUCCESS -> toast("发送成功")

                else -> true
            }
        }
    }

    /**
     * 发送格式化好的登录数据给服务端
     */
    fun sendLoginInfo(byteArray: ByteArray) {
        Thread {
            try {
                val outs = socket.getOutputStream()
                outs.write(byteArray)
                val ins = socket.getInputStream()
                val bytes = ByteArray(512)
                ins.read(bytes)
                parseResult(bytes, SEND_LOGIN)
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }.start()
    }

    /**
     * 发送格式化好的定位数据给服务端
     */
    fun sendPositionInfo(byteArray: ByteArray) {
        Thread {
            try {
                val outs = socket.getOutputStream()
                outs.write(byteArray)
                val ins = socket.getInputStream()
                val bytes = ByteArray(512)
                ins.read(bytes)
                parseResult(bytes, SEND_GPS)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    /**
     * 对服务端返回的bytearray型数据进行解析
     * type是请求类型
     */
    fun parseResult(byteArray: ByteArray, type: Int) {
        val r = cparse(byteArray)
        if (r < 0) {
            //中断连接，重新连接
            when (type) {
                SEND_GPS, SEND_LOGIN ->
                    //中断连接，重新连接
                    handler.sendEmptyMessage(LOGIN_FAILED)
                else -> true
            }
        } else if (r == 0) {
            //登录成功，发送GPS数据
            when (type) {
                SEND_GPS ->
                    handler.sendEmptyMessage(SEND_GPS_SUCCESS)
                SEND_LOGIN ->
                    //登录成功，发送GPS数据
                    handler.sendEmptyMessage(LOGIN_SUCCESS)
                else -> true
            }
        } else {
            when (type) {
            //特殊情况，直接将刚才得到的数据返回服务器
                SEND_LOGIN ->
                    handler.sendEmptyMessage(LOGIN_FAILED)
                else -> true
            }
            handler.sendEmptyMessage(LOGIN_STRAIGHT)
        }
    }

}

