<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    
    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>

    <xsl:template match="persons/person">
        <xsl:value-of select="lastname"/>,<xsl:value-of select="firstname"/>
    </xsl:template>

    <xsl:template match="projects/project" name="ImprimirClientes">
        <xsl:value-of select="name"/>
    </xsl:template>

    <xsl:template match="//clients/client">
        <xsl:call-template name="ImprimirClientes"/>
    </xsl:template>

</xsl:stylesheet>