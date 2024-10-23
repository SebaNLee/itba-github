<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>
    
    <xsl:template match="//clients/client">
        <xsl:call-template name="printClient"/>
    </xsl:template>

    <xsl:template name="printClient">
        <xsl:value-of select="./name"/>
    </xsl:template>
    
    <xsl:template match="//persons/person">
        <xsl:call-template name="printPerson"/>
    </xsl:template>
    
    <xsl:template name="printPerson">
        <xsl:value-of select="./lastname"/>, <xsl:value-of select="./firstname"/>
    </xsl:template>
    
    <xsl:template match="//projects/project">
        <xsl:call-template name="printProject"/>
    </xsl:template>
    
    <xsl:template name="printProject">
        <xsl:value-of select="./name"/>
    </xsl:template>
</xsl:stylesheet>