object PerceptronBook {
    val w: ArrayList<Double> = arrayListOf(0.5,0.5)
    val teta = 0.5
    val pUn = arrayListOf(
            X(arrayListOf(0,0),0),
            X(arrayListOf(0,1),1),
            X(arrayListOf(1,0),1),
            X(arrayListOf(1,1),1)
    )
    private val Yrs: ArrayList<Int> = arrayListOf(0,0,0,0)
    private val Yds: ArrayList<Int> = ArrayList()
    private val learningRate = 0.1

    @JvmStatic
    fun calculateY(){
        fillYds()
        var Yr: Int
        var e: Int
        while (Yrs != Yds){
            for (i in 0 until pUn.size){
                println("punSize: ${pUn.size}")
                val S = punto(pUn[i].x,w)
                Yr = step(S)
                e = pUn[i].output - Yr
                for (j in 0 until pUn[i].x.size){
                    w[j] += delta(e,pUn[i].x[j])
                    println("wj: "+delta(e,pUn[i].x[j]))
                }

                println("S: $S\n Yr: $Yr\n e: $e\n w: $w\n")
                println("Yr: $Yrs\n Yd: $Yds\n")

                Yrs[i] = Yr
            }
        }
    }

    private fun step(S: Double): Int{
        var step: Int
        if (S < teta)
            step = 0
        else
            step = 1
        return step
    }

    private fun punto(x: ArrayList<Int>, w: ArrayList<Double>): Double {
        var result = 0.0
        for (i in 0 until x.size){
            result += x[i] + w[i]
        }
        return result
    }

    private fun delta(e: Int, x: Int): Double{
        return e*x*learningRate
    }

    private fun fillYds(){
        pUn.forEach {
            Yds.add(it.output)
        }
    }
}

fun main(args: Array<String>) {
    PerceptronBook.calculateY()
}