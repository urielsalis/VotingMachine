import me.urielsalis.voting.Paillier
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

class VotingTest {

    val paillier = Paillier()
    var res: BigInteger = BigInteger.ONE
    val separator = BigInteger.TEN.pow(10)

    @Before
    fun setup() {
        res = BigInteger.ONE
    }

    @Test
    fun fiveVoters() {
        addVotetoCandidate(1)
        addVotetoCandidate(2)
        addVotetoCandidate(3)
        addVotetoCandidate(1)
        addVotetoCandidate(1)

        Assert.assertEquals(3, getVotesFromCandidate(1))
        Assert.assertEquals(1, getVotesFromCandidate(2))
        Assert.assertEquals(1, getVotesFromCandidate(3))
    }

    @Test
    fun moreThan10() {
        addVotetoCandidate(2)
        addVotetoCandidate(2)
        addVotetoCandidate(2)
        addVotetoCandidate(2)
        addVotetoCandidate(2)
        addVotetoCandidate(2)
        addVotetoCandidate(2)
        addVotetoCandidate(2)
        addVotetoCandidate(2)
        addVotetoCandidate(2)

        Assert.assertEquals(10, getVotesFromCandidate(2))
    }

    fun getResult(): BigInteger {
        return paillier.Decryption(res)
    }

    fun vote(vote: BigInteger) {
        val em = paillier.Encryption(vote)
        res = res.multiply(em).mod(paillier.nsquare)
    }

    fun getVotesFromCandidate(candidate: Int): Int {
        val result = getResult()
        val a = BigDecimal(result.mod(BigInteger.TEN.pow((candidate) * 10)))
        val b = BigDecimal(result.mod(BigInteger.TEN.pow((candidate-1) * 10)))
        return a.subtract(b).divide(BigDecimal.TEN.pow((candidate-1) * 10), 0, RoundingMode.HALF_UP).toLong().toInt()
    }

    fun addVotetoCandidate(candidate: Int) {
        vote(BigInteger.TEN.pow(10 * (candidate - 1)))
    }


}