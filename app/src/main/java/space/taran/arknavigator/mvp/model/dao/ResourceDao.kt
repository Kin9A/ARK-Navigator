package space.taran.arknavigator.mvp.model.dao

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import space.taran.arknavigator.utils.StringPath

data class ResourceWithExtra(
    @Embedded
    val resource: Resource,
    @Relation(
        parentColumn = "id",
        entityColumn = "resource"
    )
    val extras: List<ResourceExtra>
)

@Dao
interface ResourceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResources(resources: List<Resource>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExtras(resources: List<ResourceExtra>)

    @Query("DELETE FROM Resource where path in (:paths)")
    suspend fun deletePaths(paths: List<StringPath>)

    @Query("SELECT * FROM Resource where root = :root")
    suspend fun query(root: StringPath): List<ResourceWithExtra>
}
