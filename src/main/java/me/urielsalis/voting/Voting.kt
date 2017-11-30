package me.urielsalis.voting

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

class Voting private constructor() {
    var paillier: Paillier = Paillier()
    var currentResult = BigInteger.ONE

    private object Holder { val INSTANCE = Voting() }

    companion object {
        val instance: Voting by lazy { Holder.INSTANCE }
    }

    fun getResult(): BigInteger {
        return paillier.Decryption(currentResult)
    }

    fun vote(vote: BigInteger) {
        currentResult = currentResult.multiply(paillier.Encryption(vote)).mod(paillier.nsquare)
    }

    fun getVotesFromCandidate(candidate: Int): Int {
        if(candidate < 1 || candidate > 7) {
            return 0
        }
        val result = getResult()
        val a = BigDecimal(result.mod(BigInteger.TEN.pow((candidate) * 10)))
        val b = BigDecimal(result.mod(BigInteger.TEN.pow((candidate-1) * 10)))
        return a.subtract(b).divide(BigDecimal.TEN.pow((candidate-1) * 10), 0, RoundingMode.HALF_UP).toLong().toInt()
    }

    fun addVotetoCandidate(candidate: Int) {
        vote(BigInteger.TEN.pow(10 * (candidate-1)))
    }

    fun alreadyVoted(voterID: String?, voterPin: String?): Boolean {
        return false
    }
}
