open class BaseFragment : MvpAppCompatFragment(){

    protected val bind = createBinder()

    override fun onDestroyView() {
        super.onDestroyView()
        bind.resetViews()
    }
}
