/*
 * Copyright DataStax, Inc.
 *
 * Please see the included license file for details.
 */
package com.datastax.mgmtapi;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.mgmtapi.shim.CassandraAPI4x;
import com.datastax.mgmtapi.shims.CassandraAPI;
import org.apache.cassandra.service.StorageService;

public class ShimLoader
{
    private static final Logger LOGGER = LoggerFactory.getLogger(NodeOpsProvider.class);
    public static final Supplier<CassandraAPI> instance = Suppliers.memoize(ShimLoader::loadShim);

    private static CassandraAPI loadShim()
    {
        String version = StorageService.instance.getReleaseVersion();

        if (version.startsWith("4."))
            return new CassandraAPI4x();

        throw new RuntimeException("No Cassandra API Shim found for version: " + version);
    }

}
