package es.icp.logs.firebase;

public interface ListenerFirebase {
    void ok(int code, Object object);
    void error(int code, Object object);
}
