(ns baoqu.types.event)

(defrecord Event [id
                  name
                  description
                  circle-size
                  agreement-factor])

(defn event
  ([id name description circle-size agreement-factor]
   {:pre [(int? id)
          (string? name)
          (not-empty name)
          (string? description)
          (not-empty description)
          (int? circle-size)
          (int? agreement-factor)]}
   (->Event id name description circle-size agreement-factor))
  ([{:keys [id name description circle-size agreement-factor]}]
   (event id name description circle-size agreement-factor)))

(defn is-event?
  [data]
  (instance? Event data))
