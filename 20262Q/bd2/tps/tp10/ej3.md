
Cassandra es CAP AP, Availability Partition tolerance

Sacrifica Consistency (es eventualmente consistente) por poder ser rápido y permitir particiones

Los nodos guardan datos, con redundancia configurable, y para cada consulta se hace una consulta a nodos que guarden el dato (también configurable) para chequear consistencia (esto lo hace el nodo coordinador, a quien le llega la consulta del cliente básicamente)

Los updates de datos son eventualmente consistentes, primero se escriben en la memtable y commit log para luego eventualmente bajarlos a sstable (persistencia)

Si algún nodo muere durante un delete, tiene tiempo para recuperarse dentro del tiempo de gracia (otros nodos retienen el tombstone (marca de borrado) durante ese tiempo). Si se recupera dentro de ese tiempo por hints o repair entonces queda consistente. Si fuese por fuera queda inconsistente y se debe hacer mantenimiento manual

Se puede configurar para mayor velocidad (sacrifica consistencia, replication_factor bajo, USING CONSISTENCY ONE, por ej) o mayor consistencia (sacrifica velocidad, replication_factor alto, USING CONSISTENCY ALL, por ej)
