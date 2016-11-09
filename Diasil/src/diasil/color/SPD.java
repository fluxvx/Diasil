package diasil.color;

public abstract class SPD
{
    public abstract float evaluate(float wavelength);
	
	
    
    
    public static SPD constant(final float v)
    {
        return new SPD()
        {
            public float evaluate(float wavelength)
            {
                return v;
            }
            
        };
    }
    
    public static SPD linearCombination(final SPD[] spd, final float[] f)
    {
        return new SPD()
        {
            public float evaluate(float w)
            {
                float r = 0.0f;
                for (int i=0; i<spd.length; ++i)
                {
                    r += spd[i].evaluate(w)*f[i];
                }
                return r;
            }
            
        };
    }
    
    
    public static SPD scale(final SPD spd, final float scale)
    {
        return new SPD()
        {
            public float evaluate(float wavelength)
            {
                return spd.evaluate(wavelength)*scale;
            }
            
        };
    }
    
    public static SPD getGaussianDistribution(final float min, final float a, final float b, final float wavelength)
    {
        return new SPD()
        {
            public float evaluate(float w)
            {
                float wls = (w-wavelength);
                return min+a*(float)Math.exp(-b*wls*wls);
            }
            
        };
    }
    
    public static SPD getGaussianDistribution2(final float a, final float wavelength)
    {
        return new SPD()
        {
            public float evaluate(float w)
            {
                float wls = (w-wavelength);
                return Math.max(0.0f, (float)Math.exp(-a*wls*wls) - (float)Math.exp(-a));
            }
            
        };
    }
    
    
    
    
    public static SPD getBlackbodyDistribution(final float t)
    {
        return new SPD()
        {
            public float evaluate(float wl)
            {
                float wlm = wl*1.0E-9f;

                float wl5m = wlm*wlm;
                wl5m *= wl5m;
                wl5m *= wlm;

                float two_hcc2 = 1.19104259E-16f;
                float hcOk = 0.0143877506f;

                float ef = hcOk/(wlm*t);

                return two_hcc2/(wl5m*((float)Math.exp(ef) - 1.0f));
            }
            
        };
    }
    
    
    public static SPDTable getIlluminantA()
    {
        float[] values = new float[] {0.930483f,
                                        1.128210f,
                                        1.357690f,
                                        1.622190f,
                                        1.925080f,
                                        2.269800f,
                                        2.659810f,
                                        3.098610f,
                                        3.589680f,
                                        4.136480f,
                                        4.742380f,
                                        5.410700f,
                                        6.144620f,
                                        6.947200f,
                                        7.821350f,
                                        8.769800f,
                                        9.795100f,
                                        10.899600f,
                                        12.085300f,
                                        13.354300f,
                                        14.708000f,
                                        16.148000f,
                                        17.675300f,
                                        19.290700f,
                                        20.995000f,
                                        22.788300f,
                                        24.670900f,
                                        26.642500f,
                                        28.702700f,
                                        30.850800f,
                                        33.085900f,
                                        35.406800f,
                                        37.812100f,
                                        40.300200f,
                                        42.869300f,
                                        45.517400f,
                                        48.242300f,
                                        51.041800f,
                                        53.913200f,
                                        56.853900f,
                                        59.861100f,
                                        62.932000f,
                                        66.063500f,
                                        69.252500f,
                                        72.495900f,
                                        75.790300f,
                                        79.132600f,
                                        82.519300f,
                                        85.947000f,
                                        89.412400f,
                                        92.912000f,
                                        96.442300f,
                                        100.000000f,
                                        103.582000f,
                                        107.184000f,
                                        110.803000f,
                                        114.436000f,
                                        118.080000f,
                                        121.731000f,
                                        125.386000f,
                                        129.043000f,
                                        132.697000f,
                                        136.346000f,
                                        139.988000f,
                                        143.618000f,
                                        147.235000f,
                                        150.836000f,
                                        154.418000f,
                                        157.979000f,
                                        161.516000f,
                                        165.028000f,
                                        168.510000f,
                                        171.963000f,
                                        175.383000f,
                                        178.769000f,
                                        182.118000f,
                                        185.429000f,
                                        188.701000f,
                                        191.931000f,
                                        195.118000f,
                                        198.261000f,
                                        201.359000f,
                                        204.409000f,
                                        207.411000f,
                                        210.365000f,
                                        213.268000f,
                                        216.120000f,
                                        218.920000f,
                                        221.667000f,
                                        224.361000f,
                                        227.000000f,
                                        229.585000f,
                                        232.115000f,
                                        234.589000f,
                                        237.008000f,
                                        239.370000f,
                                        241.675000f};
        return new SPDTable(300.0f, 780.0f, values);
    }
    
    public static SPDTable getD65()
    {
        float[] values = new float[]{0.034100f,
                                    1.664300f,
                                    3.294500f,
                                    11.765200f,
                                    20.236000f,
                                    28.644700f,
                                    37.053500f,
                                    38.501100f,
                                    39.948800f,
                                    42.430200f,
                                    44.911700f,
                                    45.775000f,
                                    46.638300f,
                                    49.363700f,
                                    52.089100f,
                                    51.032300f,
                                    49.975500f,
                                    52.311800f,
                                    54.648200f,
                                    68.701500f,
                                    82.754900f,
                                    87.120400f,
                                    91.486000f,
                                    92.458900f,
                                    93.431800f,
                                    90.057000f,
                                    86.682300f,
                                    95.773600f,
                                    104.865000f,
                                    110.936000f,
                                    117.008000f,
                                    117.410000f,
                                    117.812000f,
                                    116.336000f,
                                    114.861000f,
                                    115.392000f,
                                    115.923000f,
                                    112.367000f,
                                    108.811000f,
                                    109.082000f,
                                    109.354000f,
                                    108.578000f,
                                    107.802000f,
                                    106.296000f,
                                    104.790000f,
                                    106.239000f,
                                    107.689000f,
                                    106.047000f,
                                    104.405000f,
                                    104.225000f,
                                    104.046000f,
                                    102.023000f,
                                    100.000000f,
                                    98.167100f,
                                    96.334200f,
                                    96.061100f,
                                    95.788000f,
                                    92.236800f,
                                    88.685600f,
                                    89.345900f,
                                    90.006200f,
                                    89.802600f,
                                    89.599100f,
                                    88.648900f,
                                    87.698700f,
                                    85.493600f,
                                    83.288600f,
                                    83.493900f,
                                    83.699200f,
                                    81.863000f,
                                    80.026800f,
                                    80.120700f,
                                    80.214600f,
                                    81.246200f,
                                    82.277800f,
                                    80.281000f,
                                    78.284200f,
                                    74.002700f,
                                    69.721300f,
                                    70.665200f,
                                    71.609100f,
                                    72.979000f,
                                    74.349000f,
                                    67.976500f,
                                    61.604000f,
                                    65.744800f,
                                    69.885600f,
                                    72.486300f,
                                    75.087000f,
                                    69.339800f,
                                    63.592700f,
                                    55.005400f,
                                    46.418200f,
                                    56.611800f,
                                    66.805400f,
                                    65.094100f,
                                    63.382800f,
                                    63.843400f,
                                    64.304000f,
                                    61.877900f,
                                    59.451900f,
                                    55.705400f,
                                    51.959000f,
                                    54.699800f,
                                    57.440600f,
                                    58.876500f,
                                    60.312500f};
        return new SPDTable(300.0f, 830.0f, values);
    }
}

