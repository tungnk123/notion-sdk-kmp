package io.github.tungnk123.notionsdkkmp.core.data.mapper

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.response.RetrieveDatabaseSchemaPropertyDto
import io.github.tungnk123.notionsdkkmp.core.data.model.result.database.NotionDatabasePropertySchema

internal fun RetrieveDatabaseSchemaPropertyDto.Number.Format.toDomain(): NotionDatabasePropertySchema.Number.Format =
    when (this) {
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Number -> NotionDatabasePropertySchema.Number.Format.Number
        RetrieveDatabaseSchemaPropertyDto.Number.Format.NumberWithCommas -> NotionDatabasePropertySchema.Number.Format.NumberWithCommas
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Percent -> NotionDatabasePropertySchema.Number.Format.Percent
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Dollar -> NotionDatabasePropertySchema.Number.Format.Dollar
        RetrieveDatabaseSchemaPropertyDto.Number.Format.CanadianDollar -> NotionDatabasePropertySchema.Number.Format.CanadianDollar
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Euro -> NotionDatabasePropertySchema.Number.Format.Euro
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Pound -> NotionDatabasePropertySchema.Number.Format.Pound
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Yen -> NotionDatabasePropertySchema.Number.Format.Yen
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Ruble -> NotionDatabasePropertySchema.Number.Format.Ruble
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Rupee -> NotionDatabasePropertySchema.Number.Format.Rupee
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Won -> NotionDatabasePropertySchema.Number.Format.Won
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Yuan -> NotionDatabasePropertySchema.Number.Format.Yuan
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Real -> NotionDatabasePropertySchema.Number.Format.Real
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Lira -> NotionDatabasePropertySchema.Number.Format.Lira
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Rupiah -> NotionDatabasePropertySchema.Number.Format.Rupiah
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Franc -> NotionDatabasePropertySchema.Number.Format.Franc
        RetrieveDatabaseSchemaPropertyDto.Number.Format.HongKongDollar -> NotionDatabasePropertySchema.Number.Format.HongKongDollar
        RetrieveDatabaseSchemaPropertyDto.Number.Format.NewZealandDollar -> NotionDatabasePropertySchema.Number.Format.NewZealandDollar
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Krona -> NotionDatabasePropertySchema.Number.Format.Krona
        RetrieveDatabaseSchemaPropertyDto.Number.Format.NorwegianKrone -> NotionDatabasePropertySchema.Number.Format.NorwegianKrone
        RetrieveDatabaseSchemaPropertyDto.Number.Format.MexicanPeso -> NotionDatabasePropertySchema.Number.Format.MexicanPeso
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Rand -> NotionDatabasePropertySchema.Number.Format.Rand
        RetrieveDatabaseSchemaPropertyDto.Number.Format.NewTaiwanDollar -> NotionDatabasePropertySchema.Number.Format.NewTaiwanDollar
        RetrieveDatabaseSchemaPropertyDto.Number.Format.DanishKrone -> NotionDatabasePropertySchema.Number.Format.DanishKrone
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Zloty -> NotionDatabasePropertySchema.Number.Format.Zloty
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Baht -> NotionDatabasePropertySchema.Number.Format.Baht
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Forint -> NotionDatabasePropertySchema.Number.Format.Forint
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Koruna -> NotionDatabasePropertySchema.Number.Format.Koruna
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Shekel -> NotionDatabasePropertySchema.Number.Format.Shekel
        RetrieveDatabaseSchemaPropertyDto.Number.Format.ChileanPeso -> NotionDatabasePropertySchema.Number.Format.ChileanPeso
        RetrieveDatabaseSchemaPropertyDto.Number.Format.PhilippinePeso -> NotionDatabasePropertySchema.Number.Format.PhilippinePeso
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Dirham -> NotionDatabasePropertySchema.Number.Format.Dirham
        RetrieveDatabaseSchemaPropertyDto.Number.Format.ColombianPeso -> NotionDatabasePropertySchema.Number.Format.ColombianPeso
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Riyal -> NotionDatabasePropertySchema.Number.Format.Riyal
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Ringgit -> NotionDatabasePropertySchema.Number.Format.Ringgit
        RetrieveDatabaseSchemaPropertyDto.Number.Format.Leu -> NotionDatabasePropertySchema.Number.Format.Leu
    }