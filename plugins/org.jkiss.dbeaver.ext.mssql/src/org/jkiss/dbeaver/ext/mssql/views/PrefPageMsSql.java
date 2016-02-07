/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2015 Serge Rieder (serge@jkiss.org)
 * Copyright (C) 2011-2012 Eugene Fradkin (eugene.fradkin@gmail.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (version 2)
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.jkiss.dbeaver.ext.mssql.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.jkiss.dbeaver.core.DBeaverCore;
import org.jkiss.dbeaver.model.DBPPreferenceStore;
import org.jkiss.dbeaver.model.DBPDataSourceContainer;
import org.jkiss.dbeaver.ui.UIUtils;
import org.jkiss.dbeaver.ui.preferences.PreferenceStoreDelegate;
import org.jkiss.dbeaver.ui.preferences.TargetPrefPage;
import org.jkiss.dbeaver.utils.PrefUtils;
import org.jkiss.dbeaver.model.sql.SQLConstants;
import org.jkiss.dbeaver.ext.mssql.model.SQLServerConstants;
import org.jkiss.dbeaver.ModelPreferences;

/**
 * PrefPageMsSql
 */
public class PrefPageMsSql extends TargetPrefPage {
    public static final String PAGE_ID = "org.jkiss.dbeaver.preferences.mssql.general"; //$NON-NLS-1$

    private Button goBatchCheck;

    public PrefPageMsSql() {
        super();
        setPreferenceStore(new PreferenceStoreDelegate(DBeaverCore.getGlobalPreferenceStore()));
    }

    @Override
    protected boolean hasDataSourceSpecificOptions(DBPDataSourceContainer dataSourceDescriptor)
    {
        return false;
    }

    @Override
    protected boolean supportsDataSourceSpecificOptions() {
        return true;
    }

    @Override
    protected Control createPreferenceContent(Composite parent) {
        Composite composite = UIUtils.createPlaceholder(parent, 1);

        {
            Group planGroup = UIUtils.createControlGroup(composite, "Misc", 2, GridData.FILL_HORIZONTAL, 0);
            goBatchCheck = UIUtils.createLabelCheckbox(planGroup, "Use GO as Batch Seperator", true);
        }

        return composite;
    }

    @Override
    protected void loadPreferences(DBPPreferenceStore store) {
        goBatchCheck.setSelection(store.getBoolean(SQLServerConstants.PREF_SUPPORT_BATCH_GO));
    }

    @Override
    protected void savePreferences(DBPPreferenceStore store) {
        final String extraDelimiters = goBatchCheck.getSelection() ? SQLServerConstants.BATCH_SEPERATOR : SQLConstants.DEFAULT_STATEMENT_DELIMITER;

        store.setValue(ModelPreferences.SCRIPT_STATEMENT_DELIMITER, extraDelimiters);
        PrefUtils.savePreferenceStore(store);
    }

    @Override
    protected void clearPreferences(DBPPreferenceStore store) {
        store.setToDefault(ModelPreferences.SCRIPT_STATEMENT_DELIMITER);
    }

    @Override
    public void applyData(Object data) {
        super.applyData(data);
    }

    @Override
    protected String getPropertyPageID() {
        return PAGE_ID;
    }
}