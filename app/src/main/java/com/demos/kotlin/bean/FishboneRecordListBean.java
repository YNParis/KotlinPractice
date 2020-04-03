package com.demos.kotlin.bean;

import java.util.List;

/**
 * 警情鱼骨图列表查询结果
 * 接口：fish/searchFishboneRecordListByAlarm
 */
public class FishboneRecordListBean {


    /**
     * code : 200
     * data : [{"cellStyleMap":{},"recordId":"1","composeId":"0001","operatorTime":"2020/03/31 11:28:47","operatorState":3,"operatorTitle":"接收警情详情","operatorContent":"","operatorType":1,"detail":{"alarmInfo":{"id":"0001","callPersonName":"三哥","callPersonPhone":"110110110","callAddress":"就是传说中的天堂","callSex":1,"deptId":"87","policeStationId":"1","callTime":"2020-03-31 11:05:03","lng":"113.072","lat":"40.959","acceptanceNumber":"00000000000000000001","region":"110","incidentAddress":"乌蒙医院撞车了","alarmContent":"瞎逼搞 没正事","eventNumber":"0000000000000001","processeType":"1","alarmStaffNumber":"000001","alarmStaffName":"赵四","alarmDuration":"3小时 玩成了聊天","alarmStarttime":"开始时间","alarmEndtime":"结束时间","alarmMode":1,"acceptanceStatus":"啥状态","alertLevel":4,"caseAction":"没事打110聊天"},"file":[{"fid":"1","fileName":"1.doc","fileLocation":"","url":"","relationId":"1","fileType":""}]},"relationId":"","alarmList":[{"cellStyleMap":{},"policeType":1,"policeName":"测试官","pcode":"P00001"}]}]
     * msg :
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "FishboneRecordListBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "cellStyleMap=" + cellStyleMap +
                    ", recordId='" + recordId + '\'' +
                    ", composeId='" + composeId + '\'' +
                    ", operatorTime='" + operatorTime + '\'' +
                    ", operatorState=" + operatorState +
                    ", operatorTitle='" + operatorTitle + '\'' +
                    ", operatorContent='" + operatorContent + '\'' +
                    ", operatorType=" + operatorType +
                    ", detail=" + detail +
                    ", relationId='" + relationId + '\'' +
                    ", alarmList=" + alarmList +
                    '}';
        }

        /**
         * cellStyleMap : {}
         * recordId : 1
         * composeId : 0001
         * operatorTime : 2020/03/31 11:28:47
         * operatorState : 3
         * operatorTitle : 接收警情详情
         * operatorContent :
         * operatorType : 1
         * detail : {"alarmInfo":{"id":"0001","callPersonName":"三哥","callPersonPhone":"110110110","callAddress":"就是传说中的天堂","callSex":1,"deptId":"87","policeStationId":"1","callTime":"2020-03-31 11:05:03","lng":"113.072","lat":"40.959","acceptanceNumber":"00000000000000000001","region":"110","incidentAddress":"乌蒙医院撞车了","alarmContent":"瞎逼搞 没正事","eventNumber":"0000000000000001","processeType":"1","alarmStaffNumber":"000001","alarmStaffName":"赵四","alarmDuration":"3小时 玩成了聊天","alarmStarttime":"开始时间","alarmEndtime":"结束时间","alarmMode":1,"acceptanceStatus":"啥状态","alertLevel":4,"caseAction":"没事打110聊天"},"file":[{"fid":"1","fileName":"1.doc","fileLocation":"","url":"","relationId":"1","fileType":""}]}
         * relationId :
         * alarmList : [{"cellStyleMap":{},"policeType":1,"policeName":"测试官","pcode":"P00001"}]
         */

        private CellStyleMapBean cellStyleMap;
        private String recordId;
        private String composeId;
        private String operatorTime;
        private int operatorState;
        private String operatorTitle;
        private String operatorContent;
        private int operatorType;
        private DetailBean detail;
        private String relationId;
        private List<AlarmListBean> alarmList;

        public CellStyleMapBean getCellStyleMap() {
            return cellStyleMap;
        }

        public void setCellStyleMap(CellStyleMapBean cellStyleMap) {
            this.cellStyleMap = cellStyleMap;
        }

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }

        public String getComposeId() {
            return composeId;
        }

        public void setComposeId(String composeId) {
            this.composeId = composeId;
        }

        public String getOperatorTime() {
            return operatorTime;
        }

        public void setOperatorTime(String operatorTime) {
            this.operatorTime = operatorTime;
        }

        public int getOperatorState() {
            return operatorState;
        }

        public void setOperatorState(int operatorState) {
            this.operatorState = operatorState;
        }

        public String getOperatorTitle() {
            return operatorTitle;
        }

        public void setOperatorTitle(String operatorTitle) {
            this.operatorTitle = operatorTitle;
        }

        public String getOperatorContent() {
            return operatorContent;
        }

        public void setOperatorContent(String operatorContent) {
            this.operatorContent = operatorContent;
        }

        public int getOperatorType() {
            return operatorType;
        }

        public void setOperatorType(int operatorType) {
            this.operatorType = operatorType;
        }

        public DetailBean getDetail() {
            return detail;
        }

        public void setDetail(DetailBean detail) {
            this.detail = detail;
        }

        public String getRelationId() {
            return relationId;
        }

        public void setRelationId(String relationId) {
            this.relationId = relationId;
        }

        public List<AlarmListBean> getAlarmList() {
            return alarmList;
        }

        public void setAlarmList(List<AlarmListBean> alarmList) {
            this.alarmList = alarmList;
        }

        public static class CellStyleMapBean {
            @Override
            public String toString() {
                return "CellStyleMapBean{}";
            }
        }

        public static class DetailBean {
            @Override
            public String toString() {
                return "DetailBean{" +
                        "alarmInfo=" + alarmInfo +
                        ", file=" + file +
                        '}';
            }

            /**
             * alarmInfo : {"id":"0001","callPersonName":"三哥","callPersonPhone":"110110110","callAddress":"就是传说中的天堂","callSex":1,"deptId":"87","policeStationId":"1","callTime":"2020-03-31 11:05:03","lng":"113.072","lat":"40.959","acceptanceNumber":"00000000000000000001","region":"110","incidentAddress":"乌蒙医院撞车了","alarmContent":"瞎逼搞 没正事","eventNumber":"0000000000000001","processeType":"1","alarmStaffNumber":"000001","alarmStaffName":"赵四","alarmDuration":"3小时 玩成了聊天","alarmStarttime":"开始时间","alarmEndtime":"结束时间","alarmMode":1,"acceptanceStatus":"啥状态","alertLevel":4,"caseAction":"没事打110聊天"}
             * file : [{"fid":"1","fileName":"1.doc","fileLocation":"","url":"","relationId":"1","fileType":""}]
             */

            private AlarmInfoBean alarmInfo;
            private List<FileBean> file;

            public AlarmInfoBean getAlarmInfo() {
                return alarmInfo;
            }

            public void setAlarmInfo(AlarmInfoBean alarmInfo) {
                this.alarmInfo = alarmInfo;
            }

            public List<FileBean> getFile() {
                return file;
            }

            public void setFile(List<FileBean> file) {
                this.file = file;
            }

            public static class AlarmInfoBean {
                /**
                 * id : 0001
                 * callPersonName : 三哥
                 * callPersonPhone : 110110110
                 * callAddress : 就是传说中的天堂
                 * callSex : 1
                 * deptId : 87
                 * policeStationId : 1
                 * callTime : 2020-03-31 11:05:03
                 * lng : 113.072
                 * lat : 40.959
                 * acceptanceNumber : 00000000000000000001
                 * region : 110
                 * incidentAddress : 乌蒙医院撞车了
                 * alarmContent : 瞎逼搞 没正事
                 * eventNumber : 0000000000000001
                 * processeType : 1
                 * alarmStaffNumber : 000001
                 * alarmStaffName : 赵四
                 * alarmDuration : 3小时 玩成了聊天
                 * alarmStarttime : 开始时间
                 * alarmEndtime : 结束时间
                 * alarmMode : 1
                 * acceptanceStatus : 啥状态
                 * alertLevel : 4
                 * caseAction : 没事打110聊天
                 */

                private String id;
                private String callPersonName;
                private String callPersonPhone;
                private String callAddress;
                private int callSex;
                private String deptId;
                private String policeStationId;
                private String callTime;
                private String lng;
                private String lat;
                private String acceptanceNumber;
                private String region;
                private String incidentAddress;
                private String alarmContent;
                private String eventNumber;
                private String processeType;
                private String alarmStaffNumber;
                private String alarmStaffName;
                private String alarmDuration;
                private String alarmStarttime;
                private String alarmEndtime;
                private int alarmMode;
                private String acceptanceStatus;
                private int alertLevel;
                private String caseAction;

                @Override
                public String toString() {
                    return "AlarmInfoBean{" +
                            "id='" + id + '\'' +
                            ", callPersonName='" + callPersonName + '\'' +
                            ", callPersonPhone='" + callPersonPhone + '\'' +
                            ", callAddress='" + callAddress + '\'' +
                            ", callSex=" + callSex +
                            ", deptId='" + deptId + '\'' +
                            ", policeStationId='" + policeStationId + '\'' +
                            ", callTime='" + callTime + '\'' +
                            ", lng='" + lng + '\'' +
                            ", lat='" + lat + '\'' +
                            ", acceptanceNumber='" + acceptanceNumber + '\'' +
                            ", region='" + region + '\'' +
                            ", incidentAddress='" + incidentAddress + '\'' +
                            ", alarmContent='" + alarmContent + '\'' +
                            ", eventNumber='" + eventNumber + '\'' +
                            ", processeType='" + processeType + '\'' +
                            ", alarmStaffNumber='" + alarmStaffNumber + '\'' +
                            ", alarmStaffName='" + alarmStaffName + '\'' +
                            ", alarmDuration='" + alarmDuration + '\'' +
                            ", alarmStarttime='" + alarmStarttime + '\'' +
                            ", alarmEndtime='" + alarmEndtime + '\'' +
                            ", alarmMode=" + alarmMode +
                            ", acceptanceStatus='" + acceptanceStatus + '\'' +
                            ", alertLevel=" + alertLevel +
                            ", caseAction='" + caseAction + '\'' +
                            '}';
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getCallPersonName() {
                    return callPersonName;
                }

                public void setCallPersonName(String callPersonName) {
                    this.callPersonName = callPersonName;
                }

                public String getCallPersonPhone() {
                    return callPersonPhone;
                }

                public void setCallPersonPhone(String callPersonPhone) {
                    this.callPersonPhone = callPersonPhone;
                }

                public String getCallAddress() {
                    return callAddress;
                }

                public void setCallAddress(String callAddress) {
                    this.callAddress = callAddress;
                }

                public int getCallSex() {
                    return callSex;
                }

                public void setCallSex(int callSex) {
                    this.callSex = callSex;
                }

                public String getDeptId() {
                    return deptId;
                }

                public void setDeptId(String deptId) {
                    this.deptId = deptId;
                }

                public String getPoliceStationId() {
                    return policeStationId;
                }

                public void setPoliceStationId(String policeStationId) {
                    this.policeStationId = policeStationId;
                }

                public String getCallTime() {
                    return callTime;
                }

                public void setCallTime(String callTime) {
                    this.callTime = callTime;
                }

                public String getLng() {
                    return lng;
                }

                public void setLng(String lng) {
                    this.lng = lng;
                }

                public String getLat() {
                    return lat;
                }

                public void setLat(String lat) {
                    this.lat = lat;
                }

                public String getAcceptanceNumber() {
                    return acceptanceNumber;
                }

                public void setAcceptanceNumber(String acceptanceNumber) {
                    this.acceptanceNumber = acceptanceNumber;
                }

                public String getRegion() {
                    return region;
                }

                public void setRegion(String region) {
                    this.region = region;
                }

                public String getIncidentAddress() {
                    return incidentAddress;
                }

                public void setIncidentAddress(String incidentAddress) {
                    this.incidentAddress = incidentAddress;
                }

                public String getAlarmContent() {
                    return alarmContent;
                }

                public void setAlarmContent(String alarmContent) {
                    this.alarmContent = alarmContent;
                }

                public String getEventNumber() {
                    return eventNumber;
                }

                public void setEventNumber(String eventNumber) {
                    this.eventNumber = eventNumber;
                }

                public String getProcesseType() {
                    return processeType;
                }

                public void setProcesseType(String processeType) {
                    this.processeType = processeType;
                }

                public String getAlarmStaffNumber() {
                    return alarmStaffNumber;
                }

                public void setAlarmStaffNumber(String alarmStaffNumber) {
                    this.alarmStaffNumber = alarmStaffNumber;
                }

                public String getAlarmStaffName() {
                    return alarmStaffName;
                }

                public void setAlarmStaffName(String alarmStaffName) {
                    this.alarmStaffName = alarmStaffName;
                }

                public String getAlarmDuration() {
                    return alarmDuration;
                }

                public void setAlarmDuration(String alarmDuration) {
                    this.alarmDuration = alarmDuration;
                }

                public String getAlarmStarttime() {
                    return alarmStarttime;
                }

                public void setAlarmStarttime(String alarmStarttime) {
                    this.alarmStarttime = alarmStarttime;
                }

                public String getAlarmEndtime() {
                    return alarmEndtime;
                }

                public void setAlarmEndtime(String alarmEndtime) {
                    this.alarmEndtime = alarmEndtime;
                }

                public int getAlarmMode() {
                    return alarmMode;
                }

                public void setAlarmMode(int alarmMode) {
                    this.alarmMode = alarmMode;
                }

                public String getAcceptanceStatus() {
                    return acceptanceStatus;
                }

                public void setAcceptanceStatus(String acceptanceStatus) {
                    this.acceptanceStatus = acceptanceStatus;
                }

                public int getAlertLevel() {
                    return alertLevel;
                }

                public void setAlertLevel(int alertLevel) {
                    this.alertLevel = alertLevel;
                }

                public String getCaseAction() {
                    return caseAction;
                }

                public void setCaseAction(String caseAction) {
                    this.caseAction = caseAction;
                }
            }

            public static class FileBean {
                @Override
                public String toString() {
                    return "FileBean{" +
                            "fid='" + fid + '\'' +
                            ", fileName='" + fileName + '\'' +
                            ", fileLocation='" + fileLocation + '\'' +
                            ", url='" + url + '\'' +
                            ", relationId='" + relationId + '\'' +
                            ", fileType='" + fileType + '\'' +
                            '}';
                }

                /**
                 * fid : 1
                 * fileName : 1.doc
                 * fileLocation :
                 * url :
                 * relationId : 1
                 * fileType :
                 */


                private String fid;
                private String fileName;
                private String fileLocation;
                private String url;
                private String relationId;
                private String fileType;

                public String getFid() {
                    return fid;
                }

                public void setFid(String fid) {
                    this.fid = fid;
                }

                public String getFileName() {
                    return fileName;
                }

                public void setFileName(String fileName) {
                    this.fileName = fileName;
                }

                public String getFileLocation() {
                    return fileLocation;
                }

                public void setFileLocation(String fileLocation) {
                    this.fileLocation = fileLocation;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getRelationId() {
                    return relationId;
                }

                public void setRelationId(String relationId) {
                    this.relationId = relationId;
                }

                public String getFileType() {
                    return fileType;
                }

                public void setFileType(String fileType) {
                    this.fileType = fileType;
                }
            }
        }

        public static class AlarmListBean {
            @Override
            public String toString() {
                return "AlarmListBean{" +
                        "cellStyleMap=" + cellStyleMap +
                        ", policeType=" + policeType +
                        ", policeName='" + policeName + '\'' +
                        ", pcode='" + pcode + '\'' +
                        '}';
            }

            /**
             * cellStyleMap : {}
             * policeType : 1
             * policeName : 测试官
             * pcode : P00001
             */


            private CellStyleMapBean cellStyleMap;
            private int policeType;
            private String policeName;
            private String pcode;

            public CellStyleMapBean getCellStyleMap() {
                return cellStyleMap;
            }

            public void setCellStyleMap(CellStyleMapBean cellStyleMap) {
                this.cellStyleMap = cellStyleMap;
            }

            public int getPoliceType() {
                return policeType;
            }

            public void setPoliceType(int policeType) {
                this.policeType = policeType;
            }

            public String getPoliceName() {
                return policeName;
            }

            public void setPoliceName(String policeName) {
                this.policeName = policeName;
            }

            public String getPcode() {
                return pcode;
            }

            public void setPcode(String pcode) {
                this.pcode = pcode;
            }
        }
    }
}
