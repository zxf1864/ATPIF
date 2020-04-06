package com.fsck.k9.ui.crypto;


import android.content.Context;

import org.openintents.openpgp.IOpenPgpService2;
import org.openintents.openpgp.util.OpenPgpApi;


public class OpenPgpApiFactory {
    OpenPgpApi createOpenPgpApi(Context context, IOpenPgpService2 service) {
        return new OpenPgpApi(context, service);
    }
}
