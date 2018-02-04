package com.me.eng.domain.reports;

import java.io.File;

/**
 *
 * @author Matheus
 */
public interface ReportGenerator
{
    public void generateReport( SampleReport report, File out ) throws Exception;
}
