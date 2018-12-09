fun String.countA(): Int {
    var array = this.toCharArray()
    var i = 0
    array.forEach { item ->
        if (item == 'a' || item == 'A')
            i++
    }
    return i
}