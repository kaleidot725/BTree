import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ComposeUIViewController
import jp.albites.btree.appModule
import jp.albites.btree.osModule
import jp.albites.btree.util.openUrl
import jp.albites.btree.view.App
import platform.Foundation.NSCoder
import platform.UIKit.UIStatusBarStyle
import platform.UIKit.UIView
import platform.UIKit.UIViewAutoresizingFlexibleHeight
import platform.UIKit.UIViewAutoresizingFlexibleWidth
import platform.UIKit.UIViewController
import platform.UIKit.addChildViewController
import platform.UIKit.didMoveToParentViewController

fun MainViewController(): UIViewController {
    return MainUIViewController().apply {
        setContent {
            App(
                openUrl = { openUrl(it) },
                onChangedDarkMode = { setDarkMode(it) },
                modules = listOf(appModule, osModule),
            )
        }
    }
}

private const val UIStatusBarStyleDark = 1L
private const val UIStatusBarStyleLight = 3L
private fun getStyle(isDarkMode: Boolean) =
    if (isDarkMode) UIStatusBarStyleDark else UIStatusBarStyleLight

private class MainUIViewController : UIViewController {
    @OverrideInit
    constructor() : super(nibName = null, bundle = null)

    @OverrideInit
    constructor(coder: NSCoder) : super(coder)

    private var isDarkMode: Boolean = false
        set(value) {
            field = value
            setNeedsStatusBarAppearanceUpdate()
        }

    private var content: @Composable () -> Unit = {}

    fun setContent(content: @Composable () -> Unit) {
        this.content = content
    }

    fun setDarkMode(isDarkMode: Boolean) {
        this.isDarkMode = isDarkMode
        setNeedsStatusBarAppearanceUpdate()
    }

    override fun preferredStatusBarStyle(): UIStatusBarStyle = getStyle(isDarkMode)

    override fun loadView() {
        super.loadView()
        val rootView = UIView()
        val composeViewController = ComposeUIViewController(content)
        addChildViewController(composeViewController)
        rootView.addSubview(composeViewController.view)
        composeViewController.view.setAutoresizingMask(
            UIViewAutoresizingFlexibleWidth or UIViewAutoresizingFlexibleHeight,
        )
        view = rootView
        composeViewController.didMoveToParentViewController(this)
    }
}
