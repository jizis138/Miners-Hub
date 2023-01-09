//package ru.vsibi.momento.data.db
//
//import android.content.Context
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import androidx.sqlite.db.SupportSQLiteOpenHelper
//import net.sqlcipher.SQLException
//import net.sqlcipher.database.SupportFactory
//import ru.sberins.insureapp.core.environment.Environment
//import ru.sberins.insureapp.data.KeyValueStorage
//import ru.sberins.insureapp.data.stored
//import ru.sberins.insureapp.util.Catch
//import ru.sberins.insureapp.util.crypto.KeyFactory
//import kotlin.reflect.KClass
//
//class SecureDbFactory(
//    storageFactory: KeyValueStorage.Factory,
//    private val context: Context,
//    private val environment: Environment,
//) : DatabaseFactory {
//
//    private var encryptionKey: ByteArray? by storageFactory
//        .create("database.prefs")
//        .stored("KEY_ENCRYPTION_KEY")
//
//    override fun <T : RoomDatabase> create(dbClass: KClass<T>, name: String): Lazy<T> = lazy {
//        val openHelperFactory = SupportFactory(
//            encryptionKey ?: KeyFactory.createSecretKey().encoded.also(::encryptionKey::set)
//        )
//        /** Сразу открываем БД, чтобы проверить соответствие ключа шифрования */
//        try {
//            create(openHelperFactory, dbClass, name).also { db ->
//                db.openHelper.readableDatabase
//            }
//        } catch (error: SQLException) {
//            Catch.log(error, "Failed to open the database with the current key.")
//            context.deleteDatabase(name)
//            create(openHelperFactory, dbClass, name)
//        } catch (error: IllegalStateException) {
//            /**
//             * При изменении схемы БД удаляем старую
//             */
//            Catch.log(error, "Failed to open database")
//            context.deleteDatabase(name)
//            create(openHelperFactory, dbClass, name)
//        }
//    }
//
//    private fun <T : RoomDatabase> create(
//        openHelperFactory: SupportSQLiteOpenHelper.Factory,
//        dbClass: KClass<T>,
//        name: String,
//    ): T = Room.databaseBuilder(context, dbClass.java, name)
//        .apply {
//            this.fallbackToDestructiveMigration()
//
//            if (!environment.isDebug) {
//                /**
//                 * Шифрование БД происходит только в релизных сборках
//                 */
//                this.openHelperFactory(openHelperFactory)
//            }
//        }
//        .build()
//}