package store.service

import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

class App{

    companion object{
        const val STORE_DB = "store-db"
        val client = KMongo.createClient().coroutine.getDatabase("store-db") //get com.mongodb.MongoClient new instance

    }
}