package com.randomx.travel.activity.wishlist

import android.view.View
import android.widget.ImageView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.google.common.truth.Truth.assertThat
import com.randomx.travel.CoroutineTestRule
import com.randomx.travel.R
import com.randomx.travel.data.local.FakeWishlistRepo
import com.randomx.travel.data.local.ServiceLocator
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class WishlistListFragmentTest
{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private lateinit var wishlistRepo: FakeWishlistRepo

    private val cancun = FakeWishlistRepo.emptyWishlist("Paquete Cancun")
    private val puerto = FakeWishlistRepo.emptyWishlist("Paquete Puerto Vallarta")
    private val cabos = FakeWishlistRepo.emptyWishlist("Paquete Los Cabods")

    @Before
    fun setUp() {

        wishlistRepo = FakeWishlistRepo()
        wishlistRepo.insertAll(listOf(cancun,puerto,cabos))
        ServiceLocator.repository = wishlistRepo;

    }


    @After
    fun tearDown() {
        ServiceLocator.resetRepository();
    }

    @Test
    fun perform_click_on_delete_button_removes_wish_from_list() {
        launchFragmentInContainer<WishlistListFragment>(null, R.style.AppTheme)
        Thread.sleep(1_000)

        Espresso
            .onView(withId(R.id.list))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(
                        ViewMatchers.withText("Paquete Cancun")
                    ),
                    itemAction(R.id.button_delete)
                )
            )


        Thread.sleep(1_000)
        assertThat(wishlistRepo.getAll().value).doesNotContain(cancun)
        assertThat(wishlistRepo.getAll().value).contains(cabos)
        assertThat(wishlistRepo.getAll().value).contains(puerto)



    }
}



fun itemAction(viewId: Int) = object : ViewAction {
    override fun getDescription() = "Click on action button"

    override fun getConstraints() = null

    override fun perform(uiController: UiController?, view: View?) {
        val button = view?.findViewById<ImageView>(viewId)
        button?.performClick();
    }
}