<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>

    <xsl:template match="//persons">
        <xsl:for-each select="person[age > 28]">
            <xsl:value-of select="." />
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="//projects" />
    <xsl:template match="//clients" />  

</xsl:stylesheet>