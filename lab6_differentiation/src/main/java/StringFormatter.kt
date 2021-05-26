import java.util.*

object StringFormatter {
    fun center(fmtStr: String?, fmt: Formatter, obj: Any?, width: Int): String {
        val str: String
        str = try {
            val tmp = Formatter()
            tmp.format(fmtStr, obj)
            tmp.toString()
        } catch (exc: IllegalFormatException) {
            println("Неверный запрос формата\n")
            fmt.format("")
            return fmt.toString()
        }
        val dif = width - str.length
        if (dif < 0) {
            fmt.format(str)
            return fmt.toString()
        }
        var pad = CharArray(dif / 2)
        Arrays.fill(pad, ' ')
        fmt.format(String(pad))
        fmt.format(str)
        pad = CharArray(width - dif / 2 - str.length)
        Arrays.fill(pad, ' ')
        fmt.format(String(pad))
        return fmt.toString()
    }

    fun formatStingCenter(line: String?, n: Int): String {
        return center("%s", Formatter(), line, n)
    }
}