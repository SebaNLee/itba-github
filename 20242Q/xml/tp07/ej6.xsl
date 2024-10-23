<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <people>
            <xsl:apply-templates />
        </people>
    </xsl:template>

    <xsl:template match="//projects" />
    <xsl:template match="//clients" />

    <xsl:template match="//persons/person">
            <person>
                <xsl:attribute name="document" select="document"/>
                <xsl:attribute name="firstname" select="firstname"/>
                <xsl:attribute name="lastname" select="lastname"/>
                <xsl:attribute name="age" select="age"/>
            </person>
    </xsl:template>
</xsl:stylesheet>

<!-- java net.sf.saxon.Transform -s:data.xml -xsl:ej6.xsl -->