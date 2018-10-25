package com.sqsong.wanandroid.ui.home.mvp

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding2.view.RxView
import com.sqsong.wanandroid.BaseApplication
import com.sqsong.wanandroid.common.event.FabClickEvent
import com.sqsong.wanandroid.mvp.BasePresenter
import com.sqsong.wanandroid.mvp.IModel
import com.sqsong.wanandroid.network.CookieManager
import com.sqsong.wanandroid.ui.home.adapter.FragmentPagerAdapter
import com.sqsong.wanandroid.ui.home.fragment.HomeFragment
import com.sqsong.wanandroid.ui.home.fragment.KnowledgeFragment
import com.sqsong.wanandroid.ui.home.fragment.NavigationFragment
import com.sqsong.wanandroid.ui.home.fragment.ProjectFragment
import com.sqsong.wanandroid.util.Constants
import com.sqsong.wanandroid.util.PreferenceHelper.set
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class MainPresenter @Inject constructor(private val mainView: MainContract.View,
                                        private val disposable: CompositeDisposable) :
        BasePresenter<MainContract.View, IModel>(null, disposable) {

    @Inject
    lateinit var mContext: Context

    @Inject
    lateinit var mPreferences: SharedPreferences

    @Inject
    lateinit var mHomeFragment: HomeFragment

    @Inject
    lateinit var mKnowledgeFragment: KnowledgeFragment

    @Inject
    lateinit var mNavigationFragment: NavigationFragment

    @Inject
    lateinit var mProjectFragment: ProjectFragment

    private var mFragmentList = mutableListOf<Fragment>()

    override fun onAttach(view: MainContract.View) {
        mView = mainView
        registerEvent()
    }

    private fun registerEvent() {
        mView.setupDrawerAndToolbar()
        disposable.add(fabDisposable())
        setupFragments()
    }

    private fun fabDisposable(): Disposable {
        return RxView.clicks(mView.getFab())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    EventBus.getDefault().post(FabClickEvent(mView.getCurrentIndex()))
                }
    }

    private fun setupFragments() {
        mFragmentList.clear()
        mFragmentList.add(mHomeFragment)
        mFragmentList.add(mKnowledgeFragment)
        mFragmentList.add(mNavigationFragment)
        mFragmentList.add(mProjectFragment)
        val pagerAdapter = FragmentPagerAdapter(mView.supportFragmentManager(), mFragmentList)
        mView.setPagerAdapter(pagerAdapter)
    }

    fun loginOut() {
        BaseApplication.INSTANCE.quitApp()
        CookieManager.getInstance(mContext).clearCookieInfo()
        mView.startLoginActivity()
    }

}