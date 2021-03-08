package todo.quang.mvvm.ui.post.fragment.dialoglist

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import todo.quang.mvvm.R
import todo.quang.mvvm.databinding.FragmentDialogListAppBinding
import todo.quang.mvvm.ui.post.activity.home.PostListViewModel
import todo.quang.mvvm.ui.post.adapter.ListAppAdapter
import todo.quang.mvvm.utils.extension.themeColor

@AndroidEntryPoint
class DialogListAppFragment : DialogFragment() {
    private val viewModelShare: PostListViewModel by activityViewModels()
    private lateinit var binding: FragmentDialogListAppBinding
    private lateinit var listAppAdapter: ListAppAdapter

    companion object {
        private const val KEY_POSITION = "KEY_POSITION"

        fun newInstance(position: Int): DialogListAppFragment {
            val args = Bundle()
            args.putInt(KEY_POSITION, position)
            val fragment = DialogListAppFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        /*sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
        }*/
        return inflater.inflate(R.layout.fragment_dialog_list_app, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDialogListAppBinding.bind(requireView())
        setupView()
        setupObserve()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun setupView() {
        binding.recyclerList.setHasFixedSize(true)

        binding.recyclerList.layoutManager = GridLayoutManager(requireActivity(), 4)

        listAppAdapter = ListAppAdapter(requireActivity().packageManager) {
            openApp(it)
        }.apply {
            binding.recyclerList.adapter = this
        }
    }

    private fun setupObserve() {
        viewModelShare.groupAppInfoDataItem.observe(viewLifecycleOwner, { it ->
            it.getOrNull(arguments?.getInt(KEY_POSITION) ?: 0)?.let {
                binding.tvTitle.text = it[0].appInfoEntity.genreName
                listAppAdapter.submitList(it)
            }
        })
    }

    private fun openApp(packageName: String) {
        val launchApp = requireActivity().packageManager.getLaunchIntentForPackage(packageName)
        startActivity(launchApp)
    }
}