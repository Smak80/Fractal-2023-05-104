package Photo

import drawing.painters.FractalPainter

fun TakePhotoInOwnFormat(fp: FractalPainter, color: String, function: String): String{
    var data = ""
    data += fp.fractal.maxIterations.toString() + "\n"
    data += fp.plane.toString() + "\n"
    data += color + "\n"
    data += function + "\n"
    return data
}