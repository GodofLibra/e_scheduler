package com.example.e_scheduler

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val notices = FirebaseFirestore.getInstance().collection("notice")
    private val announcements = FirebaseFirestore.getInstance().collection("announcement")
    private val schedules = FirebaseFirestore.getInstance().collection("schedule")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cv_schedule.setOnClickListener {
            findNavController().navigate(R.id.scheduleFragment)
        }

        cv_Notice.setOnClickListener {
            findNavController().navigate(R.id.noticeActivity)
        }

        cv_ann.setOnClickListener {
            findNavController().navigate(R.id.announcementFragment)
        }

        CoroutineScope(Dispatchers.Main).launch {
            val notice = notices.get().await().toObjects(Notice::class.java)
            var str = ""
            if (notice.size > 3) {
                for (n in notice) {
                    str += n.title + "\n"
                }
            } else {
                for (n in notice) {
                    str += n.title + "\n"
                }
            }
            tv_notices.text = str

            val ann = announcements.get().await().toObjects(Announcement::class.java)
            str = ""
            if (ann.size > 3) {
                for (a in ann) {
                    str += a.title + "\n"
                }
            } else {
                for (a in ann) {
                    str += a.title + "\n"
                }
            }
            tv_announcements.text = str


            val schedule = schedules.get().await().toObjects(Schedule::class.java)
            str = ""
            if (schedule.size > 3) {
                for (a in schedule) {
                    str += a.title + "\n"
                }
            } else {
                for (a in schedule) {
                    str += a.title + "\n"
                }
            }
            tv_schedules.text = str
        }

    }
}