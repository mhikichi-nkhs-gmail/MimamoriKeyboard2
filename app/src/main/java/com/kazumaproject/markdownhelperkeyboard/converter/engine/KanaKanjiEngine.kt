package com.kazumaproject.markdownhelperkeyboard.converter.engine

import com.kazumaproject.Louds.LOUDS
import com.kazumaproject.Louds.with_term_id.LOUDSWithTermId
import com.kazumaproject.converter.graph.GraphBuilder
import com.kazumaproject.dictionary.TokenArray
import com.kazumaproject.hiraToKata
import com.kazumaproject.viterbi.FindPath

class KanaKanjiEngine {

    private lateinit var yomiTrie: LOUDSWithTermId

    private lateinit var tangoTrie: LOUDS
    private lateinit var connectionIds: ShortArray
    private lateinit var tokenArray: TokenArray

    private lateinit var rank0ArrayLBSYomi: IntArray
    private lateinit var rank1ArrayLBSYomi: IntArray
    private lateinit var rank1ArrayIsLeaf: IntArray
    private lateinit var rank0ArrayTokenArrayBitvector: IntArray
    private lateinit var rank1ArrayTokenArrayBitvector: IntArray
    private lateinit var rank0ArrayLBSTango: IntArray
    private lateinit var rank1ArrayLBSTango: IntArray

    private lateinit var graphBuilder: GraphBuilder
    private lateinit var findPath: FindPath

    private lateinit var yomiLBSBooleanArray: BooleanArray

    fun buildEngine(
        graphBuilder: GraphBuilder,
        findPath: FindPath,
        connectionIdList: ShortArray,
        tangoTrie: LOUDS,
        yomiTrie: LOUDSWithTermId,
        tokenArray: TokenArray,
        rank0ArrayLBSYomi: IntArray,
        rank1ArrayLBSYomi: IntArray,
        rank1ArrayIsLeaf: IntArray,
        rank0ArrayTokenArrayBitvector: IntArray,
        rank1ArrayTokenArrayBitvector: IntArray,
        rank0ArrayLBSTango: IntArray,
        rank1ArrayLBSTango: IntArray,
        yomiLBSBooleanArray: BooleanArray
    ){
        this@KanaKanjiEngine.graphBuilder = graphBuilder
        this@KanaKanjiEngine.findPath = findPath

        this@KanaKanjiEngine.connectionIds = connectionIdList
        this@KanaKanjiEngine.tangoTrie = tangoTrie
        this@KanaKanjiEngine.tokenArray = tokenArray
        this@KanaKanjiEngine.yomiTrie = yomiTrie

        this@KanaKanjiEngine.rank0ArrayLBSYomi = rank0ArrayLBSYomi
        this@KanaKanjiEngine.rank1ArrayLBSYomi = rank1ArrayLBSYomi
        this@KanaKanjiEngine.rank1ArrayIsLeaf = rank1ArrayIsLeaf
        this@KanaKanjiEngine.rank0ArrayTokenArrayBitvector = rank0ArrayTokenArrayBitvector
        this@KanaKanjiEngine.rank1ArrayTokenArrayBitvector = rank1ArrayTokenArrayBitvector
        this@KanaKanjiEngine.rank0ArrayLBSTango = rank0ArrayLBSTango
        this@KanaKanjiEngine.rank1ArrayLBSTango = rank1ArrayLBSTango

        this@KanaKanjiEngine.yomiLBSBooleanArray = yomiLBSBooleanArray

    }

    fun buildEngine(
        yomi: LOUDSWithTermId,
        tango: LOUDS,
        token: TokenArray,
        connectionIdList: ShortArray
    ){
        this.yomiTrie = yomi
        this.tangoTrie = tango
        this.connectionIds = connectionIdList
        this.tokenArray = token
    }

    suspend fun nBestPath(
        input: String,
        n: Int
    ): List<String> {
        val graph = graphBuilder.constructGraph(
            input,
            yomiTrie,
            tangoTrie,
            tokenArray,
            rank0ArrayLBSYomi,
            rank1ArrayLBSYomi,
            rank1ArrayIsLeaf,
            rank0ArrayTokenArrayBitvector,
            rank1ArrayTokenArrayBitvector,
            rank0ArrayLBSTango = rank0ArrayLBSTango,
            rank1ArrayLBSTango = rank1ArrayLBSTango,
            LBSBooleanArray = yomiLBSBooleanArray
        )
        val result = findPath.backwardAStar(graph, input.length, connectionIds, n)
        result.apply {
            if (!this.contains(input)){
                add(input)
            }
            if (!this.contains(input.hiraToKata())){
                add(input.hiraToKata())
            }
        }
        return result
    }

    suspend fun viterbiAlgorithm(
        input: String
    ): String {
        val graph = graphBuilder.constructGraph(
            input,
            yomiTrie,
            tangoTrie,
            tokenArray,
            rank0ArrayLBSYomi,
            rank1ArrayLBSYomi,
            rank1ArrayIsLeaf,
            rank0ArrayTokenArrayBitvector,
            rank1ArrayTokenArrayBitvector,
            rank0ArrayLBSTango = rank0ArrayLBSTango,
            rank1ArrayLBSTango = rank1ArrayLBSTango,
            LBSBooleanArray = yomiLBSBooleanArray
        )
        return findPath.viterbi(graph, input.length, connectionIds)
    }

    private fun precomputeLabelIndexMap(LBSInBoolArray: BooleanArray): Map<Int, Int> {
        val map = mutableMapOf<Int, Int>()
        var count = 0

        for (i in LBSInBoolArray.indices) {
            if (!LBSInBoolArray[i]) {
                map[count] = i + 1
                count++
            }
        }
        return map
    }
}