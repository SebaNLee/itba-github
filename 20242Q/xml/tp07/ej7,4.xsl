<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <xsl:apply-templates select=".//project" />
    </xsl:template>

    <xsl:template match="//project">
        <xsl:choose> 
            <xsl:when test="count(staff/person) > 5"> 
                <xsl:value-of select="name"/> : Proyecto con mas de 5 integrantes
            </xsl:when>
            <xsl:when test="count(staff/person) &lt; 3"> 
                <xsl:value-of select="name"/> : Proyecto con menos de 3 integrantes
            </xsl:when>
            <xsl:otherwise> 
                <xsl:value-of select="name"/> : Proyecto con entre 3 y 5 integrantes
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="//clients" />
    <xsl:template match="//persons" />  

</xsl:stylesheet>