
class TicketsListFragment: BaseFragment(),
        PagingMvpView<TicketSimpleView> {

  
    private fun errorRetryFinder(id: Int) = pageLoadingError.findViewById<View>(id)

    private val ticketsList by bind<RecyclerView>(R.id.tickets_list)
    private val root by bind<LinearLayout>(R.id.root)
    private val refresh by bind<SwipeRefreshLayout>(R.id.refresh_view)
    private val switcher by bind<ViewAnimator>(R.id.switcher_view)
    private val emptyRetry by bind<Button>(R.id.error_retry_action_view)
    private val toolbar by bind<Toolbar>(R.id.toolbar)

    private val pageLoading by bind.stuff { LayoutInflater.from(context).inflate(R.layout.cell_page_loading, root as ViewGroup, false) }
    private val pageLoadingError by bind.stuff { LayoutInflater.from(context).inflate(R.layout.cell_page_loading_error, view as ViewGroup, false) }
    private val pageErrorRetry by bind<Button>(R.id.page_error_retry, ::errorRetryFinder)

    private val adapter by bind.stuff { TicketsListAdapter(presenter){ticketsList.post { presenter.loadNextPage()} }}
    private val wrapperAdapter by bind.stuff { WrapperAdapter(adapter) }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tickets_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(view as ViewGroup){

            ticketsList.setHasFixedSize(true)
            ticketsList.layoutManager = LinearLayoutManager(context)
            ticketsList.adapter = wrapperAdapter


            refresh.setOnRefreshListener { presenter.onSwipeToRefresh() }
            emptyRetry.setOnClickListener { presenter.onRetryClick() }
            pageErrorRetry.setOnClickListener { presenter.onPageRetryClick() }
            toolbar.setNavigationOnClickListener {
                presenter.onBackClick()
            }
        }
    }

 
}
