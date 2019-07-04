package com.github.jaydeepw.matchfilter.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.DialogFragment
import com.github.jaydeepw.matchfilter.R
import com.github.jaydeepw.matchfilter.models.datasource.remote.restapi.Api

class FiltersDialogFragment : DialogFragment() {

    lateinit var extras: HashMap<String, String>
    private var checkboxHasPhotos: CheckBox? = null
    private var checkboxFavourites: CheckBox? = null
    private var cancelButton: Button? = null
    private var applyButton: Button? = null

    companion object {
        const val DATA = "data"
    }

    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter_options, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkboxHasPhotos = view.findViewById(R.id.checkboxHasPhotos)
        checkboxFavourites = view.findViewById(R.id.checkboxFavourites)
        cancelButton = view.findViewById(R.id.buttonCancel)
        applyButton = view.findViewById(R.id.buttonApply)

        cancelButton?.setOnClickListener {
            dismiss()
        }

        applyButton?.setOnClickListener {
            returnSelectedFilters()
            dismiss()
        }

        initUi()
    }

    private fun initUi() {
        checkboxHasPhotos?.isChecked = extras[Api.PARAM_HAS_PHOTO]?.toBoolean() ?: false
        checkboxFavourites?.isChecked = extras[Api.PARAM_IS_FAV]?.toBoolean() ?: false
    }

    private fun returnSelectedFilters() {
        val map = HashMap<String, String>()
        val hasPhoto = checkboxHasPhotos?.isChecked?.toString()
        if (hasPhoto != null) {
            map[Api.PARAM_HAS_PHOTO] = hasPhoto
        }

        val isFav = checkboxFavourites?.isChecked?.toString()
        if (isFav != null) {
            map[Api.PARAM_IS_FAV] = isFav
        }

        val intent = Intent()
        intent.apply {
            putExtra(Api.PARAM_HAS_PHOTO, hasPhoto)
            putExtra(Api.PARAM_IS_FAV, isFav)
        }

        intent.putExtra(DATA, map)
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
    }
}