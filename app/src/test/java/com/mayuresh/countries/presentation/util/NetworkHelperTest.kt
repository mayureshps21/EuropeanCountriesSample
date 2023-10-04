
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import com.mayuresh.countries.presentation.util.NetworkHelper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class NetworkHelperTest {

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockConnectivityManager: ConnectivityManager

    @Mock
    private lateinit var mockNetworkInfo: NetworkInfo

    private lateinit var networkHelper: NetworkHelper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        networkHelper = NetworkHelper(mockContext)
        `when`(mockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(mockConnectivityManager)
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.M])
    fun testIsNetworkConnected_SDKAboveM_ConnectedWithTRANSPORT_WIFI() {
        // Given
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val mockNetworkCapabilities = mock(NetworkCapabilities::class.java)
            val mockNetwork = mock(Network::class.java)
            `when`(mockConnectivityManager.activeNetwork).thenReturn(mockNetwork)
            `when`(mockConnectivityManager.getNetworkCapabilities(mockNetwork)).thenReturn(mockNetworkCapabilities)
            `when`(mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).thenReturn(true)

            // When
            val isConnected = networkHelper.isNetworkConnected()

            // Then
            Assert.assertTrue(isConnected)
        }
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.M])
    fun testIsNetworkConnected_SDKAboveM_ConnectedWithTRANSPORT_CELLULAR() {
        // Given
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val mockNetworkCapabilities = mock(NetworkCapabilities::class.java)
            val mockNetwork = mock(Network::class.java)
            `when`(mockConnectivityManager.activeNetwork).thenReturn(mockNetwork)
            `when`(mockConnectivityManager.getNetworkCapabilities(mockNetwork)).thenReturn(mockNetworkCapabilities)
            `when`(mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)).thenReturn(true)

            // When
            val isConnected = networkHelper.isNetworkConnected()

            // Then
            Assert.assertTrue(isConnected)
        }
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.M])
    fun testIsNetworkConnected_SDKAboveM_ConnectedWithTRANSPORT_ETHERNET() {
        // Given
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val mockNetworkCapabilities = mock(NetworkCapabilities::class.java)
            val mockNetwork = mock(Network::class.java)
            `when`(mockConnectivityManager.activeNetwork).thenReturn(mockNetwork)
            `when`(mockConnectivityManager.getNetworkCapabilities(mockNetwork)).thenReturn(mockNetworkCapabilities)
            `when`(mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)).thenReturn(true)

            // When
            val isConnected = networkHelper.isNetworkConnected()

            // Then
            Assert.assertTrue(isConnected)
        }
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.M])
    fun testIsNetworkConnected_SDKAboveM_ConnectedWithOther() {
        // Given
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val mockNetworkCapabilities = mock(NetworkCapabilities::class.java)
            val mockNetwork = mock(Network::class.java)
            `when`(mockConnectivityManager.activeNetwork).thenReturn(mockNetwork)
            `when`(mockConnectivityManager.getNetworkCapabilities(mockNetwork)).thenReturn(mockNetworkCapabilities)
            `when`(mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_USB)).thenReturn(true)

            // When
            val isConnected = networkHelper.isNetworkConnected()

            // Then
            Assert.assertTrue(!isConnected)
        }
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.M])
    fun testIsNetworkConnected_SDKAboveM_NotConnected() {
        // Given
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            `when`(mockConnectivityManager.activeNetwork).thenReturn(null)

            // When
            val isConnected = networkHelper.isNetworkConnected()

            // Then
            Assert.assertFalse(isConnected)
        }
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
    fun testIsNetworkConnected_SDKBelowM_ConnectedWithTYPE_WIFI() {
        // Given
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            `when`(mockConnectivityManager.activeNetworkInfo).thenReturn(mockNetworkInfo)
            `when`(mockNetworkInfo.type).thenReturn(ConnectivityManager.TYPE_WIFI)

            // When
            val isConnected = networkHelper.isNetworkConnected()

            // Then
            Assert.assertTrue(isConnected)
        }
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
    fun testIsNetworkConnected_SDKBelowM_ConnectedWithTYPE_MOBILE() {
        // Given
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            `when`(mockConnectivityManager.activeNetworkInfo).thenReturn(mockNetworkInfo)
            `when`(mockNetworkInfo.type).thenReturn(ConnectivityManager.TYPE_MOBILE)

            // When
            val isConnected = networkHelper.isNetworkConnected()

            // Then
            Assert.assertTrue(isConnected)
        }
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
    fun testIsNetworkConnected_SDKBelowM_ConnectedWithTYPE_ETHERNET() {
        // Given
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            `when`(mockConnectivityManager.activeNetworkInfo).thenReturn(mockNetworkInfo)
            `when`(mockNetworkInfo.type).thenReturn(ConnectivityManager.TYPE_ETHERNET)

            // When
            val isConnected = networkHelper.isNetworkConnected()

            // Then
            Assert.assertTrue(isConnected)
        }
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
    fun testIsNetworkConnected_SDKBelowM_ConnectedWithOther() {
        // Given
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            `when`(mockConnectivityManager.activeNetworkInfo).thenReturn(mockNetworkInfo)
            `when`(mockNetworkInfo.type).thenReturn(ConnectivityManager.TYPE_DUMMY)

            // When
            val isConnected = networkHelper.isNetworkConnected()

            // Then
            Assert.assertFalse(isConnected)
        }
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
    fun testIsNetworkConnected_SDKBelowM_NotConnected() {
        // Given
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            `when`(mockConnectivityManager.activeNetworkInfo).thenReturn(null)

            // When
            val isConnected = networkHelper.isNetworkConnected()

            // Then
            Assert.assertFalse(isConnected)
        }
    }
}
