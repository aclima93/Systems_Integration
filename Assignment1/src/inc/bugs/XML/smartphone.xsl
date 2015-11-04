<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <!-- Header and Footer -->
    <xsl:include href="header_footer.xsl"/>

    <xsl:template match="/">
        <html>

            <meta name="viewport" content="width=device-width, initial-scale=1"/>
            <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css"/>

            <body>

                <!-- Header -->
                <xsl:call-template name="header"/>

                <!-- Smartphone Data -->
                <xsl:apply-templates select="smartphone"/>

                <!-- Footer -->
                <xsl:call-template name="footer"/>

            </body>

        </html>
    </xsl:template>

    <xsl:template match="smartphone">

        <title>Smartphone: <xsl:value-of select="@name"/></title>

        <!-- Informação Principal -->
        <div>

            <header class="w3-container w3-blue">
                <h2>Informação Principal</h2>
            </header>
            <div class="w3-container w3-row-padding w3-margin">

                <!-- Nome -->
                <div class="w3-third" align="center">
                    <div class="w3-card-4">
                        <header class="w3-container w3-light-blue">
                            <h3>Nome</h3>
                        </header>
                        <div class="w3-container">
                            <p>
                                <xsl:value-of select="@name"/>
                            </p>
                        </div>
                    </div>
                </div>

                <!-- Marca -->
                <div class="w3-third" align="center">
                    <div class="w3-card-4">
                        <header class="w3-container w3-light-blue">
                            <h3>Marca</h3>
                        </header>
                        <div class="w3-container">
                            <p>
                                <xsl:value-of select="@brand"/>
                            </p>
                        </div>
                    </div>
                </div>

                <!-- Preço -->
                <div class="w3-third" align="center">
                    <div class="w3-card-4">
                        <header class="w3-container w3-light-blue">
                            <h3>Preço</h3>
                        </header>
                        <div class="w3-container">
                            <p>
                                <xsl:value-of select="@price"/>&#160;<xsl:value-of select="@currency"/>
                            </p>
                        </div>
                    </div>
                </div>

            </div>

            <!-- Sumário & Link -->
            <div class="w3-container w3-row-padding w3-margin">
                <div class="w3-card-4">
                    <div align="center">
                        <header class="w3-container w3-light-blue">
                            <h3>Sumário</h3>
                        </header>
                    </div>

                    <xsl:variable name="hyperlink"><xsl:value-of select="@url"/></xsl:variable>
                    <div class="w3-container">
                        <p><xsl:value-of select="@summary_data" disable-output-escaping="no"/></p>
                        <div align="center">
                            <p>For more information please follow this <a href="{$hyperlink}" target="_blank">link</a>.</p>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <div class="w3-margin-top">
            <header class="w3-container w3-green">
                <h2>Ficha Técnica</h2>
            </header>

            <!-- template for technical_data -->
            <xsl:apply-templates select="technical_data"/>

        </div>

    </xsl:template>

    <xsl:template match="technical_data">

        <!-- template for table -->
        <xsl:apply-templates select="table"/>

    </xsl:template>

    <xsl:template match="table">

        <div class="w3-row-padding w3-margin">
            <div class="w3-card-4">

                <header class="w3-container w3-light-green">
                    <h4> <xsl:value-of select="table_title"/> </h4>
                </header>

                <table class="w3-table w3-bordered w3-striped">

                    <col width="25%"/>
                    <col width="75%"/>

                    <!-- template for table_data -->
                    <xsl:apply-templates select="table_data"/>

                </table>

            </div>
        </div>

    </xsl:template>

    <xsl:template match="table_data">

        <tr>
            <td>
                <b>
                    <xsl:value-of select="data_name"/>
                </b>
            </td>
            <td>
                <xsl:value-of select="data_value"/>
            </td>
        </tr>

    </xsl:template>

</xsl:stylesheet>