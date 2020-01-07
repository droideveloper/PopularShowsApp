/*
 * Popular Shows Android Kotlin Copyright (C) 2019 Fatih, Open Soruce.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fs.pshows.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.squareup.moshi.Json;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.fs.pshows.util.C.DETAIL_TYPE_SIMILAR;

@Entity(tableName = "Shows")
public class Show implements Parcelable, Extra  {

  public final static Show EMPTY = new Show();

  @PrimaryKey(autoGenerate = true) private Long showId;

  @Json(name = "poster_path") private String posterPath;
  private Double popularity;
  private Long id;
  @Json(name = "backdrop_path") private String backdropPath;
  @Json(name = "vote_average") private Double voteAverage;
  private String overview;
  @Json(name = "firstAirDate") private Date firstAirDate;
  @Json(name = "origin_country") private List<String> originCountry;
  @Json(name = "original_language") private String originalLanguage;
  @Ignore @Json(name = "genre_ids") private long[] genreIds;
  @Json(name = "vote_count") private Long voteCount;
  private String name;
  @Json(name = "original_name") private String originalName;

  public Show() { /*used for entity creating db*/ }

  protected Show(Parcel input) {
    boolean hasShowId = input.readInt() == 1;
    if (hasShowId) {
      showId = input.readLong();
    }
    boolean hasPosterPath = input.readInt() == 1;
    if (hasPosterPath) {
      posterPath = input.readString();
    }
    boolean hasPopularity = input.readInt() == 1;
    if (hasPopularity) {
      popularity = input.readDouble();
    }
    boolean hasId = input.readInt() == 1;
    if (hasId) {
      id = input.readLong();
    }
    boolean hasBackdropPath = input.readInt() == 1;
    if (hasBackdropPath) {
      backdropPath = input.readString();
    }
    boolean hasVoteAverage = input.readInt() == 1;
    if (hasVoteAverage) {
      voteAverage = input.readDouble();
    }
    boolean hasOverview = input.readInt() == 1;
    if (hasOverview) {
      overview = input.readString();
    }
    boolean hasFirstAirDate = input.readInt() == 1;
    if (hasFirstAirDate) {
      firstAirDate = new Date(input.readLong());
    }
    boolean hasOriginCountry = input.readInt() == 1;
    if (hasOriginCountry) {
      originCountry = new ArrayList<>();
      input.readStringList(originCountry);
    }
    boolean hasOriginalLanguage = input.readInt() == 1;
    if (hasOriginalLanguage) {
      originalLanguage = input.readString();
    }
    boolean hasGenreIds = input.readInt() == 1;
    if (hasGenreIds) {
      int size = input.readInt();
      genreIds = new long[size];
      input.readLongArray(genreIds);
    }
    boolean hasVoteCount = input.readInt() == 1;
    if (hasVoteCount) {
      voteCount = input.readLong();
    }
    boolean hasName = input.readInt() == 1;
    if (hasName) {
      name = input.readString();
    }
    boolean hasOriginalName = input.readInt() == 1;
    if (hasOriginalName) {
      originalName = input.readString();
    }
  }

  @Override public int type() {
    return DETAIL_TYPE_SIMILAR;
  }

  public Long getShowId() {
    return showId;
  }

  public void setShowId(Long showId) {
    this.showId = showId;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public Double getPopularity() {
    return popularity;
  }

  public void setPopularity(Double popularity) {
    this.popularity = popularity;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBackdropPath() {
    return backdropPath;
  }

  public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
  }

  public Double getVoteAverage() {
    return voteAverage;
  }

  public void setVoteAverage(Double voteAverage) {
    this.voteAverage = voteAverage;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public Date getFirstAirDate() {
    return firstAirDate;
  }

  public void setFirstAirDate(Date firstAirDate) {
    this.firstAirDate = firstAirDate;
  }

  public List<String> getOriginCountry() {
    return originCountry;
  }

  public void setOriginCountry(List<String> originCountry) {
    this.originCountry = originCountry;
  }

  public String getOriginalLanguage() {
    return originalLanguage;
  }

  public void setOriginalLanguage(String originalLanguage) {
    this.originalLanguage = originalLanguage;
  }

  public Long getVoteCount() {
    return voteCount;
  }

  public void setVoteCount(Long voteCount) {
    this.voteCount = voteCount;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOriginalName() {
    return originalName;
  }

  public void setOriginalName(String originalName) {
    this.originalName = originalName;
  }

  public long[] getGenreIds() {
    return genreIds;
  }

  public void setGenreIds(long[] genreIds) {
    this.genreIds = genreIds;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel output, int flags) {
    boolean hasShowId = showId != null;
    output.writeInt(hasShowId ? 1 : 0);
    if (hasShowId) {
      output.writeLong(showId);
    }
    boolean hasPosterPath = !TextUtils.isEmpty(posterPath);
    output.writeInt(hasPosterPath ? 1 : 0);
    if (hasPosterPath) {
      output.writeString(posterPath);
    }
    boolean hasPopularity = popularity != null;
    output.writeInt(hasPopularity ? 1 : 0);
    if (hasPopularity) {
      output.writeDouble(popularity);
    }
    boolean hasId = id != null;
    output.writeInt(hasId ? 1 : 0);
    if (hasId) {
      output.writeLong(id);
    }
    boolean hasBackdropPath = !TextUtils.isEmpty(backdropPath);
    output.writeInt(hasBackdropPath ? 1 : 0);
    if (hasBackdropPath) {
      output.writeString(backdropPath);
    }
    boolean hasVoteAverage = voteAverage != null;
    output.writeInt(hasVoteAverage ? 1 : 0);
    if (hasVoteAverage) {
      output.writeDouble(voteAverage);
    }
    boolean hasOverview = !TextUtils.isEmpty(overview);
    output.writeInt(hasOverview ? 1 : 0);
    if (hasOverview) {
      output.writeString(overview);
    }
    boolean hasFirstAirDate = firstAirDate != null;
    output.writeInt(hasFirstAirDate ? 1 : 0);
    if (hasFirstAirDate) {
      output.writeLong(firstAirDate.getTime());
    }
    boolean hasOriginCountry = originCountry != null && !originCountry.isEmpty();
    output.writeInt(hasOriginCountry ? 1 : 0);
    if (hasOriginCountry) {
      output.writeStringList(originCountry);
    }
    boolean hasOriginalLanguage = !TextUtils.isEmpty(originalLanguage);
    output.writeInt(hasOriginalLanguage ? 1 : 0);
    if (hasOriginalLanguage) {
      output.writeString(originalLanguage);
    }
    boolean hasGenreIds = genreIds != null && genreIds.length != 0;
    output.writeInt(hasGenreIds ? 1 : 0);
    if (hasGenreIds) {
      output.writeInt(genreIds.length);
      output.writeLongArray(genreIds);
    }
    boolean hasVoteCount = voteCount != null;
    output.writeInt(hasVoteCount ? 1 : 0);
    if (hasVoteCount) {
      output.writeLong(voteCount);
    }
    boolean hasName = !TextUtils.isEmpty(name);
    output.writeInt(hasName ? 1 : 0);
    if (hasName) {
      output.writeString(name);
    }
    boolean hasOriginalName = !TextUtils.isEmpty(originalName);
    output.writeInt(hasOriginalName ? 1 : 0);
    if (hasOriginalName) {
      output.writeString(originalName);
    }
  }

  public static final Creator<Show> CREATOR = new Creator<Show>() {
    @Override public Show createFromParcel(Parcel source) {
      return new Show(source);
    }

    @Override public Show[] newArray(int size) {
      return new Show[size];
    }
  };
}
