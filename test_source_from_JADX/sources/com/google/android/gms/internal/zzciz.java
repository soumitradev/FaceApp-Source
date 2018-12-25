package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.List;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.compress.compressors.bzip2.BZip2Constants;

public final class zzciz {
    static List<zzcja<Integer>> zza = new ArrayList();
    public static zzcja<Long> zzaa = zzcja.zza("measurement.upload.realtime_upload_interval", 10000, 10000);
    public static zzcja<Long> zzab = zzcja.zza("measurement.upload.debug_upload_interval", 1000, 1000);
    public static zzcja<Long> zzac = zzcja.zza("measurement.upload.minimum_delay", 500, 500);
    public static zzcja<Long> zzad = zzcja.zza("measurement.alarm_manager.minimum_interval", 60000, 60000);
    public static zzcja<Long> zzae = zzcja.zza("measurement.upload.stale_data_deletion_interval", 86400000, 86400000);
    public static zzcja<Long> zzaf = zzcja.zza("measurement.upload.refresh_blacklisted_config_interval", 604800000, 604800000);
    public static zzcja<Long> zzag = zzcja.zza("measurement.upload.initial_upload_delay_time", 15000, 15000);
    public static zzcja<Long> zzah = zzcja.zza("measurement.upload.retry_time", 1800000, 1800000);
    public static zzcja<Integer> zzai = zzcja.zza("measurement.upload.retry_count", 6, 6);
    public static zzcja<Long> zzaj = zzcja.zza("measurement.upload.max_queue_time", 2419200000L, 2419200000L);
    public static zzcja<Integer> zzak = zzcja.zza("measurement.lifetimevalue.max_currency_tracked", 4, 4);
    public static zzcja<Integer> zzal = zzcja.zza("measurement.audience.filter_result_max_count", 200, 200);
    public static zzcja<Long> zzam = zzcja.zza("measurement.service_client.idle_disconnect_millis", (long) DNSConstants.CLOSE_TIMEOUT, (long) DNSConstants.CLOSE_TIMEOUT);
    public static zzcja<Boolean> zzan = zzcja.zza("measurement.lifetimevalue.user_engagement_tracking_enabled", false, false);
    public static zzcja<Boolean> zzao = zzcja.zza("measurement.audience.complex_param_evaluation", false, false);
    private static List<zzcja<Double>> zzap = new ArrayList();
    private static zzcja<Boolean> zzaq = zzcja.zza("measurement.log_third_party_store_events_enabled", false, false);
    private static zzcja<Boolean> zzar = zzcja.zza("measurement.log_installs_enabled", false, false);
    private static zzcja<Boolean> zzas = zzcja.zza("measurement.log_upgrades_enabled", false, false);
    private static zzcja<Boolean> zzat = zzcja.zza("measurement.log_androidId_enabled", false, false);
    static List<zzcja<Long>> zzb = new ArrayList();
    static List<zzcja<Boolean>> zzc = new ArrayList();
    static List<zzcja<String>> zzd = new ArrayList();
    public static zzcja<Boolean> zze = zzcja.zza("measurement.upload_dsid_enabled", false, false);
    public static zzcja<Boolean> zzf = zzcja.zza("measurement.event_sampling_enabled", false, false);
    public static zzcja<String> zzg = zzcja.zza("measurement.log_tag", "FA", "FA-SVC");
    public static zzcja<Long> zzh = zzcja.zza("measurement.ad_id_cache_time", 10000, 10000);
    public static zzcja<Long> zzi = zzcja.zza("measurement.monitoring.sample_period_millis", 86400000, 86400000);
    public static zzcja<Long> zzj = zzcja.zza("measurement.config.cache_time", 86400000, 3600000);
    public static zzcja<String> zzk;
    public static zzcja<String> zzl;
    public static zzcja<Integer> zzm = zzcja.zza("measurement.upload.max_bundles", 100, 100);
    public static zzcja<Integer> zzn = zzcja.zza("measurement.upload.max_batch_size", 65536, 65536);
    public static zzcja<Integer> zzo = zzcja.zza("measurement.upload.max_bundle_size", 65536, 65536);
    public static zzcja<Integer> zzp = zzcja.zza("measurement.upload.max_events_per_bundle", 1000, 1000);
    public static zzcja<Integer> zzq = zzcja.zza("measurement.upload.max_events_per_day", (int) BZip2Constants.BASEBLOCKSIZE, (int) BZip2Constants.BASEBLOCKSIZE);
    public static zzcja<Integer> zzr = zzcja.zza("measurement.upload.max_error_events_per_day", 1000, 1000);
    public static zzcja<Integer> zzs = zzcja.zza("measurement.upload.max_public_events_per_day", 50000, 50000);
    public static zzcja<Integer> zzt = zzcja.zza("measurement.upload.max_conversions_per_day", 500, 500);
    public static zzcja<Integer> zzu = zzcja.zza("measurement.upload.max_realtime_events_per_day", 10, 10);
    public static zzcja<Integer> zzv = zzcja.zza("measurement.store.max_stored_events_per_app", (int) BZip2Constants.BASEBLOCKSIZE, (int) BZip2Constants.BASEBLOCKSIZE);
    public static zzcja<String> zzw;
    public static zzcja<Long> zzx = zzcja.zza("measurement.upload.backoff_period", 43200000, 43200000);
    public static zzcja<Long> zzy = zzcja.zza("measurement.upload.window_interval", 3600000, 3600000);
    public static zzcja<Long> zzz = zzcja.zza("measurement.upload.interval", 3600000, 3600000);

    static {
        String str = "https";
        zzk = zzcja.zza("measurement.config.url_scheme", str, str);
        str = "app-measurement.com";
        zzl = zzcja.zza("measurement.config.url_authority", str, str);
        str = "https://app-measurement.com/a";
        zzw = zzcja.zza("measurement.upload.url", str, str);
    }
}
