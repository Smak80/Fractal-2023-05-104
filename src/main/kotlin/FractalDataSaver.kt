import java.io.*

object FractalDataFileSaver{
    fun saveFractalDataToFile(fractalData: FractalData, filePath: String) {
        try {
            val fileOutputStream = FileOutputStream("$filePath.fractal")
            val objectOutputStream = ObjectOutputStream(fileOutputStream)

            objectOutputStream.writeObject(fractalData)

            objectOutputStream.close()
            fileOutputStream.close()

            println("FractalData saved successfully to $filePath.fractal")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun readFractalDataFromFile(filePath: String): FractalData? {
        var fractalData: FractalData? = null

        try {
            println(filePath)
            val fileInputStream = FileInputStream(filePath)
            val objectInputStream = ObjectInputStream(fileInputStream)

            fractalData = objectInputStream.readObject() as? FractalData

            objectInputStream.close()
            fileInputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        return fractalData
    }
}