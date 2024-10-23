<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>

    <xsl:template match="//clients">
        <xsl:for-each select="client">
            <xsl:sort select="name" />
            <xsl:value-of select="." />
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="//projects" />
    <xsl:template match="//persons" />
    
</xsl:stylesheet>

<!-- java net.sf.saxon.Transform -s:data.xml -xsl:ej7,1.xsl -->