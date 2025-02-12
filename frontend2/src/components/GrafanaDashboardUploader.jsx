import React, { useState } from "react";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Alert, AlertDescription } from "@/components/ui/Alert";
import { Upload } from "lucide-react";

const GrafanaDashboardUploader = () => {
  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);

  //파일 선택 핸들러
  const handleFileChange = async (event) => {
    setError("");
    setSuccess(false);

    const file = event.target.files[0];
    if (!file || file.type !== "application/json") {
      setError("JSON 파일만 업로드 가능합니다.");
      return;
    }

    try {
      const content = await readFileContent(file);
      const uid = await sendToGrafana(content);
      setSuccess(true);
      openGrafanaDashboard(uid);
    } catch (error) {
      setError(error.message);
    }
  };

  // 파일 내용 읽기
  const readFileContent = (file) => {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onload = (e) => resolve(e.target.result);
      reader.onerror = () => reject(new Error("파일 읽기 실패"));
      reader.readAsText(file);
    });
  };

  //그라파나 API로 전송
  const sendToGrafana = async (content) => {
    const GRAFANA_API_URL = "https://your-grafana.com/api/dashboards/db";
    const API_KEY = "Bearer Your_API_KEY";

    try {
      const response = await fetch(GRAFANA_API_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: API_KEY,
        },
        body: JSON.stringify({
          dashboard: JSON.parse(content),
          overwrite: true,
        }),
      });

      const data = await response.json();
      if (!response.ok) {
        throw new Error(data.message || "업로드 실패");
      }
      return data.uid;
    } catch (error) {
      throw new Error("API 전송 오류: ${error.message}");
    }
  };

  //그라파나 대시보드 새창에서 열기
  const openGrafanaDashboard = (uid) => {
    const dashboardUrl = "https://your-grafana.com/d/${id}";
    window.open(dashboardUrl, "_blank", "noopener, noreferer");
  };

  return (
    <Card className="w-full max-w-md mx-auto">
      <CardHeader>
        <CardTitle className="flex items-center gap-2">
          <Upload className="w-5 h-5" />
          Grafana 설정 적용
        </CardTitle>
      </CardHeader>
      <CardContent>
        <div className="space-y-4">
          <div className="flex flex-col gap-2">
            <Input
              type="file"
              accept=".json"
              onChange={handleFileChange}
              className="cursor-pointer"
            />
            <p className="text-sm text-gray-500">
              JSON 파일 업로드 후 새 창에서 확인하세요.
            </p>
          </div>

          {error && (
            <Alert variant="destructive">
              <AlertDescription>{error}</AlertDescription>
            </Alert>
          )}

          {success && (
            <Alert>
              <AlertDescription>
                대시보드가 성공적으로 업로드되었습니다.
              </AlertDescription>
            </Alert>
          )}
        </div>
      </CardContent>
    </Card>
  );
};

export default GrafanaDashboardUploader;
