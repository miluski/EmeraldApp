export default function AccessDenied() {
  return (
    <div className="flex justify-center items-center min-h-screen">
      <div className="flex-column">
        <div className="flex-column text-center">
          <h1 className="text-2xl font-bold text-mui-success">401 Unauthorized</h1>
          <p className="mt-4">
            You don't have enough permissions to see this site content.
          </p>
        </div>
      </div>
    </div>
  );
}
