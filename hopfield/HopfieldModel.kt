package hopfield

object HopfieldModel {

    val Y = arrayListOf(
            arrayListOf(1,1,1,-1),  //Y1
            arrayListOf(-1,-1,-1,1) //Y2
    )

    var W: ArrayList<ArrayList<Int>> = ArrayList()
    var Identity: ArrayList<ArrayList<Int>> = ArrayList()

    //Mutable lists
    val Thresholds = arrayListOf(0,0,0,0)
    var VectorEvaluation = arrayListOf(-1,-1,-1,-1)

    @JvmStatic
    fun hopfieldModel(){

        initializeList(Y.first(), W)
        initializeList(Y.first(), Identity)

        Y.forEach {
            val yMatrix = it
            val ytMatrix = it
            val yxyt = getYxYT(yMatrix,ytMatrix)
            //println("yxyt: "+yxyt.toString())
            getWPlus(yxyt)
        }
        getIdentityMatrix(W)
        getWLessIdentity()
        //val finalW = W.clone()
        //println("w: "+finalW.toString())

        //Evaluate a vector
        EvaluateHopfield.constructor(W, VectorEvaluation, Thresholds)
    }

    private fun initializeList(data: ArrayList<Int>, currentList: ArrayList<ArrayList<Int>>){
        data.forEach {
            val tempList: ArrayList<Int> = ArrayList()
            data.forEach {
                tempList.add(0)
            }
            currentList.add(tempList)
        }
    }

    private fun getIdentityMatrix(data: ArrayList<ArrayList<Int>>){
        for (file in 0 until data.size){
            for (column in 0 until data[file].size){
                if (file == column){
                    Identity[file][column] = W[file][column]
                }
            }
        }
    }

    private fun getYxYT(Y: ArrayList<Int>,YT: ArrayList<Int>): ArrayList<ArrayList<Int>>{
        val response: ArrayList<ArrayList<Int>> = ArrayList()

        Y.forEach { YList ->
            val tempList: ArrayList<Int> = ArrayList()
            YT.forEach { YTList ->
                val currentValue = YList*YTList
                tempList.add(currentValue)
            }
            response.add(tempList)
        }

        return response
    }

    private fun getWPlus(yxyt: ArrayList<ArrayList<Int>>){
        for (file in 0 until yxyt.size){
            for (column in 0 until yxyt.size){
                W[file][column] += yxyt[file][column]
            }
        }
    }

    private fun getWLessIdentity(){
        for (file in 0 until W.size){
            for (column in 0 until W[file].size){
                W[file][column] -= Identity[file][column]
            }
        }
    }
}

fun main(args: Array<String>) {
    HopfieldModel.hopfieldModel()
}