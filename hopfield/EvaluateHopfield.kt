package hopfield

object EvaluateHopfield {
    var Thresholds: ArrayList<Int> = ArrayList()
    var W: ArrayList<ArrayList<Int>> = ArrayList()
    var VectorEvaluation: ArrayList<Int> = ArrayList()
    var Ym: ArrayList<Int> = ArrayList()

    @JvmStatic
    fun constructor(
            W: ArrayList<ArrayList<Int>>,
            VectorEvaluation: ArrayList<Int>,
            Thresholds: ArrayList<Int>
    ){
        this.W = W
        this.VectorEvaluation = VectorEvaluation
        this.Thresholds = Thresholds
        evaluateHopfield()
    }

    fun evaluateHopfield(){
        println("------Evaluacion-------")
        println("W: $W")
        println("VectorEvaluation: $VectorEvaluation")
        println("Threshold: $Thresholds")
        calculatefe()
    }

    private fun calculatefe(){
        initializeYSign()
        calculateYm()
        println("fe: $Ym") //fe = Pt x W
        val fa = calculateYSign() //fa = F(fe)
        println("fa: "+calculateYSign().toString())
        val fs = fa //fs = I(fa)
        println("fs: "+calculateYSign().toString())

        //last iteration?
        if (isFaCorrect(fa))
            println("\nTerminaron iteraciones!!\nHay covergencia!")
        else
            constructor(W, fa, Thresholds)
    }

    private fun initializeYSign(){
        Ym.removeAll(Ym)
        Thresholds.forEach {
            Ym.add(0)
        }
    }

    private fun calculateYm(){
        //Mult by vector evaluation
        for (file in 0 until W.size){
            for (column in 0 until W[file].size){
                Ym[file] += W[file][column] * VectorEvaluation[column]
                //println("${W[file][column]} * ${VectorEvaluation[column]} = ${Ym[file]}")
            }
        }
        //Less thresholds
        for (file in 0 until Ym.size){
            Ym[file] -= Thresholds[file]
        }
    }

    private fun calculateYSign(): ArrayList<Int>{
        val response: ArrayList<Int> = ArrayList()
        for (file in 0 until Ym.size){
            when{
                Ym[file] > 0 -> response.add(1)
                Ym[file] < 0 -> response.add(-1)
                Ym[file] == 0 -> response.add(VectorEvaluation[file])
            }
        }

        return response
    }

    private fun isFaCorrect(YSign: ArrayList<Int>): Boolean{
        var isLastIteration = false
        HopfieldModel.Y.forEach {
            if (it == VectorEvaluation)
                isLastIteration = true
        }
        return isLastIteration
    }
}