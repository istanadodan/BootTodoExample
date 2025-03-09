import React, { useEffect } from "react";

const OpenGrafanaDashboard = () => {
  useEffect(() => {
    const openGrafanaDashboard = () => {
      // Grafana 대시보드 URL (대시보드 UID 포함)
      const dashboardUid = "rYdddlPWk"; // 대시보드 UID
      const grafanaUrl = `http://localhost:3000/d/${dashboardUid}/node-exporter-full?orgId=1&from=now-5m&to=now&timezone=browser&refresh=1m`;

      // API 키 (Grafana에서 생성한 키)
      const jwt =
        "eyJhbGciOiJSUzUxMiJ9.eyJzdWIiOiJjNG5vd0BuYXZlci5jb20iLCJ1c2VyIjoiamF2YS1hY2NvdW50IiwiaWF0IjoxNzQxNDc5NDM3LCJleHAiOjE3NDI5NTA2NjZ9.STArDAplO43OSLPwU8gWii5zVPU16n9_aUDrt-mlQmGt7xceEaZcJrhOLc0ifIrd2IQNHNU69e0H3HLaBoUucGtEKpH7Z_31DBV2LY67PukiZzFhDU0AA397hY-HhCrVFF2typ9vu43XD2PHqP3vdJwuUG1g9Mu6t7_IWWBuuFv97V2tGzw4FmCjLPio8m247Pl3VO9a-IB8jNHBvzH-gZXCFo4HCVWFoYMf_S93KHfxtfRt1UMu_c8i0sIZs2lanO3vRCf1nmwZgj3DGSF4vJJUwZ-wRpwTXea7pUYVEb9oeQacQ7maS-a_NOZZuuJd6ZqBCu97PnU10cjw5nisisAANdrr16jyH6fUJPwODqdyTTnAO5cf4IsSVPoepvrXqwb_NZzA7U_Ej86A9E9z9PfofE-HnNY6_kxm2kaVHaCwBlTEffoXCrEZQROdAeEVmd9tlZKwIkvjxC5n0H4xa_3kqM9Ggxi5VoAxhrCcAWbEvS1SA1pFSdW2GZnpdwLU0vmTjHug_xiap5YGxPzQpLAOUuv_cvztPMSOq-KfWQsZJFtJP6oDOAfEAt63E7PH4uVTxC0iStMmSNIevkm0quOb1zP9pW1Ff099nyU3E-QLKsTM-M9e_LyeAzy1rb9KyLwElm6HL5DunSyCfroAtzYBLCRdQllVMLymae3zRVQ";

      // 새 창에서 Grafana 대시보드 열기
      const newWindow = window.open(grafanaUrl, "_blank");

      // 새 창이 로드된 후 API 키를 사용하여 인증 처리
      newWindow.onload = async () => {
        const script = document.createElement("script");
        const response = await fetch("${grafanaUrl}", {
          method: "GET",
          headers: {
            "X-JWT-Assertion": `${jwt}`,
            "Content-Type": "application/json",
          },
        });

        if (response.ok) {
          alert("successful!!");
          script.innerHTML = await response.json();
        } else {
          alert("failed!!");
        }
        alert("response: " + response);
        newWindow.document.body.appendChild(script);
      };
    };

    // 버튼 클릭 시 대시보드 열기
    const button = document.getElementById("openGrafanaButton");
    if (button) {
      button.addEventListener("click", openGrafanaDashboard);
    }

    // 컴포넌트 언마운트 시 이벤트 리스너 제거
    return () => {
      if (button) {
        button.removeEventListener("click", openGrafanaDashboard);
      }
    };
  }, []);

  return <button id="openGrafanaButton">Open Grafana Dashboard</button>;
};

export default OpenGrafanaDashboard;
