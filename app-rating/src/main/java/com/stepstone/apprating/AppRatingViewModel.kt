package com.stepstone.apprating

import androidx.lifecycle.ViewModel

class AppRatingViewModel : ViewModel() {
    
    var positiveButtonText: String = ""
    var ngativeButtonText: String = ""
    var neutralButtonText: String = ""
    var noteDescription: String = ""
    var rating: Int = 0
    var title: String = ""
    var description: String = ""
    var commentBoxVisible: Boolean = true
    var defaultComment: String = ""
    var starColor: Int = 0
    var noteDescriptionTextColor: Int = 0
    var titleTextColor: Int = 0
    var descriptionTextColor: Int = 0
    var hint: String = ""
    var hintTextColor: Int = 0
    var commentTextColor: Int = 0
    var commentBackgroundColor: Int = 0

    fun setup(data: AppRatingDialog.Builder.Data) {
        title = data.title.text!!
    }

}
