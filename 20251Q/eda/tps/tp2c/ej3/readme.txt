Lo hice en el ej2, en /CustomTheSearcher.java

.1) se van cambiando las variables String fieldName y queryStr en L42 y L43

.2) cambio el new TermQuery a new PrefixQuery

.3) cambio nuevamente la clase a TermRangeQuery