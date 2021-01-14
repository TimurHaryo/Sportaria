package id.train.sportaria.abstaction

import android.os.Parcelable
import id.train.sportaria.domain.factory.ItemTypeFactory

abstract class BaseItemModel: Parcelable {
    abstract fun type(itemTypeFactory: ItemTypeFactory): Int
}