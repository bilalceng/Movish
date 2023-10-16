package com.bilalberek.movish.Ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bilalberek.movish.R
import com.bilalberek.movish.Ui.Fragments.GenerateMovieFragment
import com.bilalberek.movish.Ui.Fragments.NewMovieFragment
import com.bilalberek.movish.Ui.Fragments.WatchLaterFragment
import com.bilalberek.movish.ViewModels.MainViewmodel
import com.etebarian.meowbottomnavigation.MeowBottomNavigation


class MainActivity : AppCompatActivity() {
     lateinit var viewmodel: MainViewmodel
    private lateinit var meowNavigation: MeowBottomNavigation
    private var selectedId: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewmodel = ViewModelProvider(this, ViewModelProvider
            .AndroidViewModelFactory.getInstance(application))
            .get(MainViewmodel::class.java)






        meowNavigation = findViewById(R.id.bottom_navigation)

        prepareBottomNavigation()

        if(savedInstanceState == null){
            meowNavigation.show(1)
        }

        manageFragmentTransaction()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedNavElementId", selectedId)
        Log.d("backStack tracker", "on saveInstanceState: $selectedId")
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val id = savedInstanceState.getInt("selectedNavElementId",-1)
        Log.d("backStack tracker", "${id}")

        if (id != -1){
            meowNavigation.show(id)
        }
    }

    private fun prepareBottomNavigation(){
         meowNavigation.add(MeowBottomNavigation.Model(1, R.drawable.new_movie))
        meowNavigation.add(MeowBottomNavigation.Model(2, R.drawable.generate))
        meowNavigation.add(MeowBottomNavigation.Model(3, R.drawable.watch_later))
    }

    private fun manageFragmentTransaction(){
        meowNavigation.setOnClickMenuListener {
            Log.d("backStack tracker", "when ${it.id}")
            selectedId = it.id
            when(it.id){
                1 -> {
                    transactNewMovieFragment()
                }
                2 -> {
                    transactGenerateMovieFragment()
                }
                3 -> {
                    transactWatchLaterFragment()
                }

            }
        }
    }


    private fun createNewMovieFragment(): NewMovieFragment {
        var newMovieFragment = supportFragmentManager.findFragmentByTag(NEW_MOVIE_FRAGMENT) as? NewMovieFragment
        if(newMovieFragment == null){
            newMovieFragment = NewMovieFragment.newInstance()

        }
        return newMovieFragment
    }

    private fun createWatchLaterFragment(): WatchLaterFragment {
        var watchLaterFragment = supportFragmentManager.findFragmentByTag(WATCH_LATER_FRAGMENT) as? WatchLaterFragment
        if(watchLaterFragment == null){
            watchLaterFragment = WatchLaterFragment.newInstance()

        }
        return watchLaterFragment
    }


    private fun createGenerateMovieFragment(): GenerateMovieFragment {
        var generateMovieFragment = supportFragmentManager.findFragmentByTag(GENERATE_MOVIE_FRAGMENT) as? GenerateMovieFragment
        if(generateMovieFragment == null){
            Log.d(TAG,"ı am here")
            generateMovieFragment = GenerateMovieFragment.newInstance()

        }
        return generateMovieFragment
    }


    @SuppressLint("SuspiciousIndentation")
    private fun transactNewMovieFragment(){
        Log.d("backStack tracker", "${supportFragmentManager.backStackEntryCount}")
        var newMovieFragment = createNewMovieFragment()

        var transaction = supportFragmentManager.beginTransaction()

        transaction.setCustomAnimations(

                R.anim.slide_in_from_right,
                R.anim.slide_out_to_left,
                R.anim.slide_in_from_left,
                R.anim.slide_out_to_right

        )

            transaction.replace(
                R.id.fragment_container,
                newMovieFragment,
                NEW_MOVIE_FRAGMENT
            ).addToBackStack(null).setReorderingAllowed(true).commit()


    }


    private fun transactGenerateMovieFragment(){
        Log.d("backStack tracker", "${supportFragmentManager.backStackEntryCount}")
        var generateMovieFragment = createGenerateMovieFragment()

        var transaction = supportFragmentManager.beginTransaction()

        transaction.setCustomAnimations(

            R.anim.slide_in_from_right,
            R.anim.slide_out_to_left,
            R.anim.slide_in_from_left,
            R.anim.slide_out_to_right

        )

            transaction.replace(
                R.id.fragment_container,
                generateMovieFragment,
                GENERATE_MOVIE_FRAGMENT
            ).addToBackStack(null).setReorderingAllowed(true).commit()


    }

    private fun transactWatchLaterFragment(){
        Log.d("backStack tracker", "${supportFragmentManager.backStackEntryCount}")
        var watchLaterFragment = createWatchLaterFragment()
        var transaction = supportFragmentManager.beginTransaction()

        transaction.setCustomAnimations(

            R.anim.slide_in_from_right,
            R.anim.slide_out_to_left,
            R.anim.slide_in_from_left,
            R.anim.slide_out_to_right

        )

          transaction.replace(
                R.id.fragment_container,
                watchLaterFragment,
                WATCH_LATER_FRAGMENT
            ).addToBackStack(null).setReorderingAllowed(true).commit()


    }


        override fun onBackPressed() {
            if (supportFragmentManager.backStackEntryCount > 1) {
                var currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
                Log.d(TAG,"$currentFragment")
                currentFragment?.let {
                    supportFragmentManager.beginTransaction().remove(it).commit()

                }
                supportFragmentManager.popBackStack()
                supportFragmentManager.executePendingTransactions()

                currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

                currentFragment?.let {
                    updateBottomNavigationSelectedItem(currentFragment)
                }


            } else {
                finish()
            }



        }

        private fun updateBottomNavigationSelectedItem( currentFragment: Fragment) {


            Log.d(TAG,"$currentFragment")
            when (currentFragment) {

                is NewMovieFragment -> meowNavigation.show(1)
                is GenerateMovieFragment -> meowNavigation.show(2)
                is WatchLaterFragment -> meowNavigation.show(3)

            }
        }

    companion object{
        const val TAG = "tracking backtrace"
        const val NEW_MOVIE_FRAGMENT = "NewMovieFragment"
        const val GENERATE_MOVIE_FRAGMENT  = "GenerateMovieFragment"
        const val WATCH_LATER_FRAGMENT = "WatchLaterFragment"
    }
}