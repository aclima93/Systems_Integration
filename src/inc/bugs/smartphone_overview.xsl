<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>

            <meta name="viewport" content="width=device-width, initial-scale=1"/>
            <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css"/>
            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css"/>

            <body>

                <!-- Search Options -->
                <div class="w3-margin-top">
                    <header class="w3-container w3-orange">
                        <h2>Search Options</h2>
                    </header>
                    <div class="w3-row-padding w3-margin-top">
                        <div class="w3-half" align="center">
                            <div class="w3-card-8">
                                <header class="w3-container w3-amber">
                                    <h4>António Lima</h4>
                                </header>
                                <div class="w3-container">
                                    <p>uc2011166926</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <!-- Smarthphones -->
                <xsl:apply-templates select="smartphones"/>



                <!-- Developer Information -->
                <div class="w3-margin-top">
                    <header class="w3-container w3-orange">
                        <h2>Developers</h2>
                    </header>
                    <div class="w3-row-padding w3-margin-top">
                        <div class="w3-half" align="center">
                            <div class="w3-card-8">
                                <header class="w3-container w3-amber">
                                    <h4>António Lima</h4>
                                </header>
                                <div class="w3-container">
                                    <p>uc2011166926</p>
                                </div>
                            </div>
                        </div>
                        <div class="w3-half" align="center">
                            <div class="w3-card-8">
                                <header class="w3-container w3-amber">
                                    <h4>Pedro Janeiro</h4>
                                </header>
                                <div class="w3-container">
                                    <p>uc2012143629</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </body>

        </html>
    </xsl:template>

    <xsl:template match="smartphones">

        <div class="w3-margin-top">
            <header class="w3-container w3-blue">
                <h2>Smartphones</h2>
            </header>
            <div class="w3-row-padding w3-margin-top">
                <div class="w3-container w3-responsive">

                    <table class="w3-table w3-bordered w3-striped w3-card-4">

                        <thead class="w3-light-blue">
                            <tr>
                                <th>Nome</th>
                                <th>Marca</th>
                                <th>Preço</th>
                                <th>More</th>
                            </tr>
                        </thead>

                        <!-- Smartphone Overviews -->
                        <xsl:apply-templates select="smartphone_overview"/>

                    </table>
                </div>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="smartphone_overview">

        <tr>
            <td>
                <xsl:value-of select="@name"/>
            </td>
            <td>
                <xsl:value-of select="@brand"/>
            </td>
            <td>
                <xsl:value-of select="@price"/>&#160;<xsl:value-of select="@currency"/>
            </td>
            <td>
                <!-- link for the page with extensive contents fo this smartphone -->
                <xsl:variable name="hyperlink"><xsl:value-of select="@smartphone_url"/></xsl:variable>
                <a class="w3-btn-floating w3-teal" href="{$hyperlink}" target="_blank">
                    <i class="fa fa-plus"/>
                </a>
            </td>
        </tr>

    </xsl:template>

</xsl:stylesheet>