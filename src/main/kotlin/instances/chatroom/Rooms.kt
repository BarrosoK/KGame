package instances.chatroom

object Rooms {

    val rooms = ArrayList<ChatRoom>()

    fun init (){
        rooms.add(ChatRoom("General", "general chat"))
        rooms.add(ChatRoom("Trade", "trading chat"))
    }

    fun getRoom(name: String) : ChatRoom? {
        return (rooms.find {
            it.name == name })
    }

}
