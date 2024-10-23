<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template name="printClient">
        <xsl:param name="nombre"/>
        <xsl:value-of select="$nombre"/>
    </xsl:template>

    <xsl:template match="//clients/client">
        <xsl:call-template name="printClient">
            <xsl:with-param name="nombre" select="./name"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="printPerson">
        <xsl:param name="nombre"/>
        <xsl:param name="apellido"/>
        <xsl:value-of select="$apellido"/>, <xsl:value-of select="$nombre"/>
    </xsl:template>

    <xsl:template match="//persons/person">
        <xsl:call-template name="printPerson">
            <xsl:with-param name="nombre" select="firstname"/>
            <xsl:with-param name="apellido" select="lastname"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="printProject">
        <xsl:param name="nombre"/>
        <xsl:value-of select="$nombre"/>
    </xsl:template>

    <xsl:template match="//projects/project">
        <xsl:call-template name="printProject">
            <xsl:with-param name="nombre" select="./name"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>
    
</xsl:stylesheet>