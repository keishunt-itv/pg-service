package com.itv.product.pg_service.config

import com.itv.product.pg_service.config.GenericObjectMapper.getMapper
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.jackson2.Jackson2Config
import org.jdbi.v3.jackson2.Jackson2Plugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.postgresql.ds.PGSimpleDataSource
import javax.sql.DataSource

object DatabaseConfig {
        private val ds = getPGDataSource()
        private val sql = SqlObjectPlugin()

        private val postgresJdbi = getJdbi(ds)
            .installPlugin(sql)

        fun jdbi(): Jdbi {
            return postgresJdbi
        }
    }

    private fun getJdbi(ds: HikariDataSource): Jdbi {
        val jdbi = Jdbi.create(ds)
            .installPlugin(PostgresPlugin())
            .installPlugin(KotlinPlugin())
            .installPlugin(Jackson2Plugin())

        jdbi.getConfig(Jackson2Config::class.java).mapper = getMapper()
        return jdbi
    }

    fun getPGDataSource(): HikariDataSource {
        val ds = PGSimpleDataSource()
        ds.serverNames = arrayOf(Properties.postgres_host)
        ds.portNumbers = intArrayOf(Properties.postgres_port)
        ds.currentSchema = Properties.postgres_schema
        ds.databaseName = Properties.postgres_database
        ds.user = Properties.postgres_username
        ds.password = Properties.postgres_password
        ds.loadBalanceHosts = true

        return getHikariDataSource(
            getHikariConfig(
                ds
            )
        )
    }

    fun getHikariDataSource(config: HikariConfig): HikariDataSource = HikariDataSource(config)

    fun getHikariConfig(ds: DataSource): HikariConfig {
        val hc = HikariConfig()

        hc.dataSource = ds
        hc.maximumPoolSize = Properties.maximum_pool_size
        hc.minimumIdle = Properties.minimum_idle_connections

        return hc
    }
