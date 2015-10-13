<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <head>
                <title>Smartphones</title>
            </head>
            <body>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="smartphone">
        <h1>Informação Principal</h1>
        <table border="1">
            <tr>
                <td>
                    <b>Preço</b>
                </td>
                <td>
                    <xsl:value-of select="@price"/> <xsl:value-of select="@currency"/>
                </td>
            </tr>
            <tr>
                <td>
                    <b>Nome</b>
                </td>
                <td>
                    <xsl:value-of select="@name"/>
                </td>
            </tr>
            <tr>
                <td>
                    <b>Marca</b>
                </td>
                <td>
                    <xsl:value-of select="@brand"/>
                </td>
            </tr>
        </table>

        <h1>Ficha Técnica</h1>

        <!-- template for table -->
        <xsl:apply-templates/>

    </xsl:template>

    <xsl:template match="table">
        <table border="1">
            <tr>
                <td>
                    <xsl:value-of select="table_title"/>
                </td>

                <!-- template for table_data -->
                <xsl:apply-templates/>

            </tr>
        </table>
    </xsl:template>

    <xsl:template match="table_data">

        <xsl:variable name="rowColor">
            <xsl:choose>
                <xsl:when test="position() mod 2 = 1">
                    <xsl:text>#efefef</xsl:text>
                </xsl:when>
                <xsl:otherwise>#ababab</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>


        <tr class="odd" style="background-color: {$rowColor};">
            <td>
                <xsl:value-of select="data_name"/>
            </td>
            <td>
                <xsl:value-of select="data_value"/>
            </td>
        </tr>
    </xsl:template>

</xsl:stylesheet>