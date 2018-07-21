package instances.items

import enums.ItemType

open class Item (val id: Int, val name: String, val itemType: ItemType){
    constructor(other: Item) : this(other.id, other.name, other.itemType)

}