package org.radarbase.iot.converter.coralenviro

import org.radarbase.data.AvroRecordData
import org.radarbase.data.RecordData
import org.radarbase.iot.converter.AvroConverter
import org.radarbase.iot.sensor.CoralEnviroLight
import org.radarbase.topic.AvroTopic
import org.radarcns.kafka.ObservationKey

class CoralEnviroLightConverter(private val topicName: String = "coral_enviro_light") :
    AvroConverter<ObservationKey, CoralEnviroLight> {
    override fun getAvroTopic(): AvroTopic<ObservationKey, CoralEnviroLight> =
        AvroTopic(
            topicName, ObservationKey.getClassSchema(), CoralEnviroLight.getClassSchema(),
            ObservationKey::class.java, CoralEnviroLight::class.java
        )

    override fun convert(messages: List<String>): RecordData<ObservationKey, CoralEnviroLight> {

        val values: List<CoralEnviroLight> = messages.map {
            CoralEnviroLight.getDecoder().decode(it.byteInputStream())
        }
        return AvroRecordData<ObservationKey, CoralEnviroLight>(
            getAvroTopic(),
            AvroConverter.genericObservationKey,
            values
        )
    }
}