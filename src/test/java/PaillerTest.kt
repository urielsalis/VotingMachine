import me.urielsalis.voting.Paillier
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.math.BigInteger


class PaillerTest {

    val paillier = Paillier()

    val m1 = BigInteger("13")
    val m2 = BigInteger("165")

    val em1 = paillier.Encryption(m1)
    val em2 = paillier.Encryption(m2)

    @Test
    fun shouldEncryptAndDecryptToTheSame() {

        val dm1 = paillier.Decryption(em1)
        val dm2 = paillier.Decryption(em2)

        Assert.assertEquals(m1.toString(), dm1.toString())
        Assert.assertEquals(m2.toString(), dm2.toString())
    }

    @Test
    fun shouldDoHomomorphicSum() {
        /* test homomorphic properties -> D(E(m1)*E(m2) mod n^2) = (m1 + m2) mod n */
        val product_em1em2 = em1.multiply(em2).mod(paillier.nsquare)
        val sum_m1m2 = m1.add(m2).mod(paillier.n)

        Assert.assertEquals(sum_m1m2.toString(), paillier.Decryption(product_em1em2).toString())
    }

    @Test
    fun shouldDoHomomorphicProduct() {
        val expo_em1m2 = em1.modPow(m2, paillier.nsquare)
        val prod_m1m2 = m1.multiply(m2).mod(paillier.n)

        Assert.assertEquals(prod_m1m2.toString(), paillier.Decryption(expo_em1m2).toString())
    }
}